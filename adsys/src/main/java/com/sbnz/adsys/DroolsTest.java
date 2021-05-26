package com.sbnz.adsys;

import com.sbnz.adsys.model.*;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.time.LocalDate;

public class DroolsTest {
    
    public static void main(String[] args) {
        try {
            // load up the knowledge base
            KieServices ks = KieServices.Factory.get();
            KieContainer kContainer = ks.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession("ksession-rules");
            
            // go !
            Advertisement ad = new Advertisement();
            ad.setDatePublished(LocalDate.now());
            Advertiser advertiser = new Advertiser();
            advertiser.setName("Google");
            ad.setAdvertiser(advertiser);

            AdvertisementRequest request = new AdvertisementRequest();

            request.setAdvertisement(ad);
            request.setGeographicLocation("Serbia");
            request.setMinAge(15);
            
            Candidate candidate = new Candidate();
            SocialMediaUser user = new SocialMediaUser();
            user.setUser(new User());
            user.getUser().setFirstName("Pera");
            user.getUser().setLastName("Peric");
            user.setAge(18);
            user.setCity("Novi Sad");
            user.setCountry("Serbia");
            candidate.setUser(user);
            
            Candidate candidate2 = new Candidate();
            SocialMediaUser user2 = new SocialMediaUser();
            user2.setUser(new User());
            user2.getUser().setFirstName("Djura");
            user2.getUser().setLastName("Djuric");
            user2.setAge(14);
            user2.setCity("Berlin");
            user2.setCountry("Germany");
            candidate2.setUser(user2);
            
            kSession.insert(request);
            kSession.insert(candidate);
            kSession.insert(candidate2);
//            kSession.fireAllRules();

            kSession.getAgenda().getAgendaGroup("target-group").setFocus();
            kSession.fireAllRules();

            kSession.getAgenda().getAgendaGroup("elimination/qualification").setFocus();
            kSession.fireAllRules();

            kSession.getAgenda().getAgendaGroup("calculating-coefficients").setFocus();
            kSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
