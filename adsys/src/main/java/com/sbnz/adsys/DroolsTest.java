package com.sbnz.adsys;

import com.sbnz.adsys.event.AdvertisementClickEvent;
import com.sbnz.adsys.event.AdvertisementViewEvent;
import com.sbnz.adsys.model.*;
import com.sbnz.adsys.service.SocialMediaUserService;
import org.drools.core.ClockType;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.time.SessionPseudoClock;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DroolsTest {
    public static Candidate getBasicCandidate() {
        Candidate candidate = new Candidate();
        SocialMediaUser user = getBasicUser();
        candidate.setUser(user);
        return candidate;
    }
    
    public static SocialMediaUser getBasicUser() {
        SocialMediaUser user = new SocialMediaUser();
        user.setId(0L);
        user.setUser(new User());
        user.getUser().setFirstName("User");
        user.getUser().setLastName("Useric");
        user.setAge(18);
        user.setCity("Belgrade");
        user.setCountry("Serbia");
        
        user.setClickedAdvertisements(new LinkedList<>());
        user.setIgnoredAdvertisements(new LinkedList<>());
        user.setLikedSocialMediaPages(new LinkedList<>());
        user.setAdvertisementsToBeShown(new LinkedList<>());
        user.setSeenAdvertisements(new LinkedList<>());
        return user;
    }
    
    public SocialMediaUser getBasicUser(String email){
        SocialMediaUser user = getBasicUser();
        user.getUser().setEmail(email);
        return user;
    }
    
    public static User createUser() {
        return new User(55L, "admin@mai.com", "admin123", "admin",
                "admin",  null, true);
    }
    
    public static Candidate getBasicCandidate(String email) {
        Candidate candidate = getBasicCandidate();
        candidate.getUser().getUser().setEmail(email);
        return candidate;
    }
    
    public static Advertisement getBasicAdvertisement() {
        Advertiser advertiser = new Advertiser();
        advertiser.setAdvertisements(new LinkedList<>());
        advertiser.setName("Google");
        
        Advertisement ad = new Advertisement();
        ad.setId(0L);
        ad.setTitle("Good search engine");
        ad.setTags(new LinkedList<>());
        ad.setDatePublished(LocalDate.now());
        ad.setAdvertiser(advertiser);
        ad.setTags(new LinkedList<>());
        
        return ad;
    }
    
    public static AdvertisementRequest getBasicAdvertisementRequest() {
        AdvertisementRequest request = new AdvertisementRequest();
        request.setAdvertisement(getBasicAdvertisement());
        request.setMaxAge(99);
        request.setMinAge(0);
        request.setGeographicLocation("");
        return request;
    }
    
    public static AdvertisementRequest getAdvertisementRequestToFitCandidate(Candidate candidate) {
        AdvertisementRequest request = getBasicAdvertisementRequest();
        int age = candidate.getUser().getAge();
        request.setMinAge(age - 1);
        request.setMaxAge(age + 1);
        request.setGeographicLocation(candidate.getUser().getCountry());
        
        return request;
    }
    
    public static List<Tag> createTags(String ...names) {
        List<Tag> tags = new LinkedList<>();
        
        for(String name: names)
            tags.add(new Tag(0L, name));
        
        return tags;
    }
    
    public static double getAverage(double ...values) {
        double sum = 0;
        
        for(double value: values)
            sum += value;
        
        return sum / values.length;
    }
    
    public static void init() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        KieSession kieSession = kc.newKieSession("advertisementEventsCep-session");
        KieSessionConfiguration ksconf = ks.newKieSessionConfiguration();
        ksconf.setOption(ClockTypeOption.get(ClockType.PSEUDO_CLOCK.getId()));
        kieSession.setGlobal("socialMediaUserService", new SocialMediaUserService());
    }
    
    public static void main(String[] args) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        
        KieSessionConfiguration ksconf = ks.newKieSessionConfiguration();
        ksconf.setOption(ClockTypeOption.get(ClockType.PSEUDO_CLOCK.getId()));
        KieSession kieSession = kc.newKieSession("advertisementEventsCep-session", ksconf);
        kieSession.setGlobal("socialMediaUserService", new SocialMediaUserService());
        SocialMediaUser user = getBasicUser();
        Advertisement ad = getBasicAdvertisement();
        user.getAdvertisementsToBeShown().add(ad);
        SessionPseudoClock clock = kieSession.getSessionClock();
        kieSession.insert(new AdvertisementViewEvent( user, ad));
        kieSession.fireAllRules();
//        try {
//            Thread.sleep(5 * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        ;
        clock.advanceTime(1, TimeUnit.MINUTES);
        kieSession.insert(new AdvertisementClickEvent( user, ad));
        kieSession.fireAllRules();
        System.out.println(user.getIgnoredAdvertisements().contains(ad));
        //assertTrue();
        //assertFalse(user.getClickedAdvertisements().contains(ad));
    }
}
