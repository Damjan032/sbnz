package com.sbnz.adsys.sevice;

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
            kSession.fireAllRules();

            kSession.fireAllRules();
            System.out.println();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public Candidate createCandidateNotTargetGroup(){
        Candidate candidate = new Candidate();
        SocialMediaUser user = new SocialMediaUser();
        user.setUser(new User());
        user.getUser().setFirstName("Djura");
        user.getUser().setLastName("Djuric");
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
        user.setAge(18);
        user.setCity("Novi Sad");
        user.setCountry("Serbia");
        user.setIgnoredAdvertisements(new LinkedList<>());
        for(int i = 0; i < 5; i++)
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
        user.setAge(18);
        user.setCity("Belgrade");
        user.setCountry("Serbia");
        user.setClickedAdvertisements(new LinkedList<>());
        for(int i = 0; i < 5; i++)
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
        return new AdvertisementRequestDTO(request.getId(), request.getMinAge(), request.getMaxAge(),
                request.getGeographicLocation(), advertisementService.toDTO(request.getAdvertisement()));
    }

    public AdvertisementRequest toEntity(AdvertisementRequestDTO requestDTO) {
        Advertisement advertisement = advertisementService.toEntity(requestDTO.getAdvertisement());
        return new AdvertisementRequest(requestDTO.getId(), requestDTO.getMinAge(), requestDTO.getMaxAge(),
                requestDTO.getGeographicLocation(), advertisement);
    }
}
