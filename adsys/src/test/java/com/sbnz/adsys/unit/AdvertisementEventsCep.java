package com.sbnz.adsys.unit;

import com.sbnz.adsys.event.AdvertisementClickEvent;
import com.sbnz.adsys.event.AdvertisementIgnoredEvent;
import com.sbnz.adsys.event.AdvertisementViewEvent;
import com.sbnz.adsys.model.Advertisement;
import com.sbnz.adsys.model.SocialMediaUser;
import com.sbnz.adsys.service.SocialMediaUserService;
import com.sbnz.adsys.util.DroolsTestUtils;

import org.drools.core.ClockType;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.time.SessionPseudoClock;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class AdvertisementEventsCep {

    private KieSession kieSession;

    @Before
    public void init() {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
    
        KieSessionConfiguration ksconf = ks.newKieSessionConfiguration();
        ksconf.setOption(ClockTypeOption.get(ClockType.PSEUDO_CLOCK.getId()));
        kieSession = kc.newKieSession("advertisementEventsCep-session", ksconf);
        kieSession.setGlobal("socialMediaUserService", new SocialMediaUserService());
        /*KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        kieSession = kc.newKieSession("advertisementEventsCep-session");
        kieSession.getSessionConfiguration().setOption(ClockTypeOption.get(ClockType.PSEUDO_CLOCK.getId()));
        kieSession.setGlobal("socialMediaUserService", new SocialMediaUserService());*/
    }

    @Test
    public void testSeenAd_UserHasSeenTheAd() {
        SocialMediaUser user = DroolsTestUtils.getBasicUser();
        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        user.getAdvertisementsToBeShown().add(ad);

        AdvertisementViewEvent event = new AdvertisementViewEvent(user, ad);
        kieSession.insert(event);
        kieSession.fireAllRules();

        assertFalse(user.getAdvertisementsToBeShown().contains(ad));
        assertTrue(user.getSeenAdvertisements().contains(ad));
    }

    @Test
    public void testClickedAd_OneMinuteAfterSeeing() {
        SocialMediaUser user = DroolsTestUtils.getBasicUser();
        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        user.getAdvertisementsToBeShown().add(ad);

        kieSession.insert(new AdvertisementViewEvent( user, ad));
        
        kieSession.insert(new AdvertisementClickEvent( user, ad));
        kieSession.fireAllRules();

        assertFalse(user.getSeenAdvertisements().contains(ad));
        assertTrue(user.getClickedAdvertisements().contains(ad));
    }

    @Test
    public void testClickedAd_ElevenMinuteAfterSeeing_ClickNotRegistered() {
        SocialMediaUser user = DroolsTestUtils.getBasicUser();
        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        user.getAdvertisementsToBeShown().add(ad);
        SessionPseudoClock clock = kieSession.getSessionClock();
        kieSession.insert(new AdvertisementViewEvent(user, ad));
        clock.advanceTime(15, TimeUnit.MINUTES);
        kieSession.insert(new AdvertisementClickEvent(user, ad));
        kieSession.fireAllRules();
        assertTrue(user.getIgnoredAdvertisements().contains(ad));
        assertFalse(user.getClickedAdvertisements().contains(ad));
    }
    
    @Test
    public void testAdWasClickedForMoreThen20TimesButNotInstant() {
        SocialMediaUser user = DroolsTestUtils.getBasicUser();
        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        user.getAdvertisementsToBeShown().add(ad);
        assertFalse(user.getSeenAdvertisements().contains(ad));
        for (int i = 0; i <10; ++i) {
            kieSession.insert(new AdvertisementClickEvent(user, ad));
            kieSession.fireAllRules();
        }
        assertFalse(user.getSeenAdvertisements().contains(ad));
        SessionPseudoClock clock = kieSession.getSessionClock();
        clock.advanceTime(15, TimeUnit.MINUTES);
        for (int i = 0; i < 15; ++i) {
            kieSession.insert(new AdvertisementClickEvent(user, ad));
            kieSession.fireAllRules();
        }
        assertFalse(user.getIgnoredAdvertisements().contains(ad));
    }
    
    @Test
    public void testAdWasClickedForLessThan10Times() {
        SocialMediaUser user = DroolsTestUtils.getBasicUser();
        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        user.getAdvertisementsToBeShown().add(ad);
        assertFalse(user.getSeenAdvertisements().contains(ad));
        for (int i = 0; i <4; ++i) {
            kieSession.insert(new AdvertisementClickEvent(user, ad));
            kieSession.fireAllRules();
        }
        assertFalse(user.getSeenAdvertisements().contains(ad));
        /*SessionPseudoClock clock = kieSession.getSessionClock();
        clock.advanceTime(15, TimeUnit.MINUTES);*/
        for (int i = 0; i < 5; ++i) {
            kieSession.insert(new AdvertisementClickEvent( user, ad));
            kieSession.fireAllRules();
        }
    
        assertFalse(user.getIgnoredAdvertisements().contains(ad));
    }
    
    @Test
    public void testIgnoredAdWasViewedForMoreThan10TimesInTheLast10Min() {
        SocialMediaUser user = DroolsTestUtils.getBasicUser();
        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        user.getAdvertisementsToBeShown().add(ad);
        for (int i = 0; i < 35; ++i) {
            kieSession.insert(new AdvertisementClickEvent(user, ad));
            kieSession.fireAllRules();
        }
        /*SessionPseudoClock clock = kieSession.getSessionClock();
        clock.advanceTime(8, TimeUnit.MINUTES);
        
        AdvertisementIgnoredEvent aie = new AdvertisementIgnoredEvent(Date.from(Instant.now().plus(8, ChronoUnit.MINUTES)), user, ad);
        kieSession.insert(aie);
        kieSession.fireAllRules();*/
    
        assertTrue(user.getIgnoredAdvertisements().contains(ad));
    }

}
