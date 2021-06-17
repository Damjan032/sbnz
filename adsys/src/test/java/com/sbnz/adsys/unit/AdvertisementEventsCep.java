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
        kieSession = kc.newKieSession("advertisementEventsCep-session");
        kieSession.getSessionConfiguration().setOption(ClockTypeOption.get(ClockType.PSEUDO_CLOCK.getId()));
        kieSession.setGlobal("socialMediaUserService", new SocialMediaUserService());
    }

    @Test
    public void testSeenAd_UserHasSeenTheAd() {
        SocialMediaUser user = DroolsTestUtils.getBasicUser();
        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        user.getAdvertisementsToBeShown().add(ad);

        AdvertisementViewEvent event = new AdvertisementViewEvent(new Date(), user, ad);
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

        kieSession.insert(new AdvertisementViewEvent(new Date(), user, ad));
        
        kieSession.insert(new AdvertisementClickEvent(Instant.now().plus(1, ChronoUnit.MINUTES).toEpochMilli(), user, ad));
        kieSession.fireAllRules();

        assertFalse(user.getSeenAdvertisements().contains(ad));
        assertTrue(user.getClickedAdvertisements().contains(ad));
    }

    @Test
    public void testClickedAd_ElevenMinuteAfterSeeing_ClickNotRegistered() {
        SocialMediaUser user = DroolsTestUtils.getBasicUser();
        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        user.getAdvertisementsToBeShown().add(ad);

        kieSession.insert(new AdvertisementViewEvent(new Date(), user, ad));
        kieSession.insert(new AdvertisementIgnoredEvent(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)), user, ad));
        kieSession.fireAllRules();
        
        assertFalse(user.getClickedAdvertisements().contains(ad));
    }
    
    @Test
    public void testAdWasClickedForLessThan10TimesButNotInstant() {
        SocialMediaUser user = DroolsTestUtils.getBasicUser();
        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        user.getAdvertisementsToBeShown().add(ad);
        assertFalse(user.getSeenAdvertisements().contains(ad));
        for (int i = 0; i <10; ++i) {
            kieSession.insert(new AdvertisementClickEvent(Instant.now().toEpochMilli(), user, ad));
            kieSession.fireAllRules();
        }
        assertFalse(user.getSeenAdvertisements().contains(ad));
        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 15; ++i) {
            kieSession.insert(new AdvertisementClickEvent(Instant.now().plus(15, ChronoUnit.MINUTES).toEpochMilli(), user, ad));
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
            kieSession.insert(new AdvertisementClickEvent(Instant.now().toEpochMilli(), user, ad));
            kieSession.fireAllRules();
        }
        assertFalse(user.getSeenAdvertisements().contains(ad));
        /*SessionPseudoClock clock = kieSession.getSessionClock();
        clock.advanceTime(15, TimeUnit.MINUTES);*/
        for (int i = 0; i < 5; ++i) {
            kieSession.insert(new AdvertisementClickEvent(Instant.now().plus(15, ChronoUnit.MINUTES).toEpochMilli(), user, ad));
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
            kieSession.insert(new AdvertisementClickEvent(Instant.now().toEpochMilli(), user, ad));
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
