package com.sbnz.adsys.service;

import com.sbnz.adsys.dto.AdvertisementRequestDTO;
import com.sbnz.adsys.model.*;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;

@Service
public class AdvertisementRequestService {

    @Autowired
    AdvertisementService advertisementService;

    private static final double COMPANY_COEFFICIENT = 1;
    private static final double CLICKED_ADS_COEFFICIENT = 1;
    private static final double IGNORED_ADS_COEFFICIENT = 1;
    private static final double LIKED_PAGES_COEFFICIENT = 1;
    private static final double HERD_COEFFICIENT = 1;


    public void submit(AdvertisementRequestDTO requestDTO) {
        AdvertisementRequest request = toEntity(requestDTO);
        droolsTest(request);
    }

    private void droolsTest(AdvertisementRequest request) {
        try {
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession("ksession-rules");

            // go !
            Candidate candidate = createCandidate5IgnoredAds();
            Candidate candidate2 = createCandidateNotTargetGroup();
            Candidate candidate3 = createCandidate5ClickedAds();
            Candidate candidate4 = createCoeffCandidate();

            kSession.insert(request);
            kSession.insert(candidate);
            kSession.insert(candidate2);
            kSession.insert(candidate3);
            kSession.insert(candidate4);

            kSession.setGlobal("COMPANY_COEFFICIENT", COMPANY_COEFFICIENT);
            kSession.setGlobal("CLICKED_ADS_COEFFICIENT", CLICKED_ADS_COEFFICIENT);
            kSession.setGlobal("IGNORED_ADS_COEFFICIENT", IGNORED_ADS_COEFFICIENT);
            kSession.setGlobal("LIKED_PAGES_COEFFICIENT", LIKED_PAGES_COEFFICIENT);
            kSession.setGlobal("HERD_COEFFICIENT", HERD_COEFFICIENT);

            kSession.fireAllRules();
            System.out.println();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public Candidate createCandidateNotTargetGroup() {
        Candidate candidate = new Candidate();
        SocialMediaUser user = new SocialMediaUser();
        user.setUser(new User());
        user.getUser().setFirstName("Djura");
        user.getUser().setLastName("Djuric");
        user.getUser().setEmail("djura");
        user.setAge(14);
        user.setCity("Berlin");
        user.setCountry("Germany");
        candidate.setUser(user);
        return candidate;
    }

    public Candidate createCandidate5IgnoredAds() {
        Candidate candidate = new Candidate();
        SocialMediaUser user = new SocialMediaUser();
        user.setUser(new User());
        user.getUser().setFirstName("Pera");
        user.getUser().setLastName("Peric");
        user.getUser().setEmail("pera");
        user.setAge(18);
        user.setCity("Novi Sad");
        user.setCountry("Serbia");
        user.setIgnoredAdvertisements(new LinkedList<>());
        for (int i = 0; i < 5; i++)
            user.getIgnoredAdvertisements().add(getAdByGoogle());

        candidate.setUser(user);
        return candidate;
    }

    public Candidate createCandidate5ClickedAds() {
        Candidate candidate = new Candidate();
        SocialMediaUser user = new SocialMediaUser();
        user.setUser(new User());
        user.getUser().setFirstName("Jovan");
        user.getUser().setLastName("Jovanovic");
        user.getUser().setEmail("jova");
        user.setAge(18);
        user.setCity("Belgrade");
        user.setCountry("Serbia");
        user.setClickedAdvertisements(new LinkedList<>());
        for (int i = 0; i < 5; i++)
            user.getClickedAdvertisements().add(getAdByGoogle());

        candidate.setUser(user);
        return candidate;
    }

    public Candidate createCoeffCandidate() {
        Candidate candidate = new Candidate();
        SocialMediaUser user = new SocialMediaUser();
        user.setUser(new User());
        user.getUser().setFirstName("Koef");
        user.getUser().setLastName("Koefic");
        user.getUser().setEmail("koef");
        user.setAge(18);
        user.setCity("Belgrade");
        user.setCountry("Serbia");

        Advertisement ad = getAdByGoogle();
        ad.getAdvertiser().setName("not google");
        ad.setTags(new LinkedList<>());
        ad.getTags().add(new Tag(0L, "technology"));

        Advertisement ad2 = getAdByGoogle();
        ad2.getAdvertiser().setName("definitely not google");
        ad2.setTags(new LinkedList<>());
        ad2.getTags().add(new Tag(0L, "retail"));

        user.setClickedAdvertisements(new LinkedList<>());
        user.getClickedAdvertisements().add(ad);
        user.getClickedAdvertisements().add(ad2);

        candidate.setUser(user);
        return candidate;
    }

    private Advertisement getAdByGoogle() {
        Advertiser advertiser = new Advertiser();
        advertiser.setName("Google");
        Advertisement advertisement = new Advertisement();
        advertisement.setAdvertiser(advertiser);
        advertisement.setDatePublished(LocalDate.now());
        return advertisement;
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
