package com.sbnz.adsys.service;

import com.sbnz.adsys.dto.AdvertisementRequestDTO;
import com.sbnz.adsys.dto.CandidateDTO;
import com.sbnz.adsys.model.*;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisementRequestService {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private KieService kieService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private SocialMediaUserService userService;

    private static final String RECOMMENDATION_SESSION = "ksession-rules";

    private static final double COMPANY_COEFFICIENT = 1;
    private static final double CLICKED_ADS_COEFFICIENT = 3;
    private static final double IGNORED_ADS_COEFFICIENT = 2;
    private static final double LIKED_PAGES_COEFFICIENT = 1;
    private static final double HERD_COEFFICIENT = 1;


    public List<CandidateDTO> submit(AdvertisementRequestDTO requestDTO) {
        AdvertisementRequest request = toEntity(requestDTO);
        advertisementService.save(request.getAdvertisement());

        KieSession kieSession = kieService.getSession(RECOMMENDATION_SESSION);

        kieSession.insert(request);
        userService.findAllEntity().forEach(user -> {
            Candidate candidate = new Candidate();
            candidate.setUser(user);
            kieSession.insert(candidate);
        });

        setGlobalVariables(kieSession);
        kieSession.fireAllRules();

        Collection<?> candidates = kieSession.getObjects((o) -> o instanceof Candidate);

        kieSession.dispose();
        kieSession.destroy();

        List<Candidate> toBeShownTo = candidates.stream()
                .map(obj -> (Candidate) obj)
                .sorted(Comparator.comparingDouble(c -> -c.getFinalScore()))
                .limit((int) request.getBudget())
                .collect(Collectors.toList());

        toBeShownTo.forEach(candidate -> {
            SocialMediaUser user = candidate.getUser();
            user.getAdvertisementsToBeShown().add(request.getAdvertisement());
            userService.save(user);
        });

        return toBeShownTo.stream()
                .map(candidate -> candidateService.toDTO(candidate))
                .collect(Collectors.toList());
    }

    private void setGlobalVariables(KieSession kieSession) {
        kieSession.setGlobal("COMPANY_COEFFICIENT", COMPANY_COEFFICIENT);
        kieSession.setGlobal("CLICKED_ADS_COEFFICIENT", CLICKED_ADS_COEFFICIENT);
        kieSession.setGlobal("IGNORED_ADS_COEFFICIENT", IGNORED_ADS_COEFFICIENT);
        kieSession.setGlobal("LIKED_PAGES_COEFFICIENT", LIKED_PAGES_COEFFICIENT);
        kieSession.setGlobal("HERD_COEFFICIENT", HERD_COEFFICIENT);
    }

    public AdvertisementRequestDTO toDTO(AdvertisementRequest request) {

        return AdvertisementRequestDTO.builder()
                .id(request.getId())
                .minAge(request.getMinAge())
                .maxAge(request.getMaxAge())
                .geographicLocation(request.getGeographicLocation())
                .budget(request.getBudget())
                .advertisement(advertisementService.toDTO(request.getAdvertisement()))
                .build();
    }

    public AdvertisementRequest toEntity(AdvertisementRequestDTO dto) {

        return AdvertisementRequest.builder()
                .id(dto.getId())
                .minAge(dto.getMinAge())
                .maxAge(dto.getMaxAge())
                .geographicLocation(dto.getGeographicLocation())
                .budget(dto.getBudget())
                .advertisement(advertisementService.toEntity(dto.getAdvertisement()))
                .build();
    }
}
