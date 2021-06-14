package com.sbnz.adsys.unit;

import com.sbnz.adsys.event.AdvertisementClickEvent;
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

        SessionPseudoClock clock = kieSession.getSessionClock();
        clock.advanceTime(1, TimeUnit.MINUTES);
        kieSession.insert(new AdvertisementClickEvent(new Date(), user, ad));
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

        SessionPseudoClock clock = kieSession.getSessionClock();
        clock.advanceTime(110, TimeUnit.MINUTES);
        kieSession.insert(new AdvertisementClickEvent(new Date(), user, ad));
        kieSession.fireAllRules();

        assertTrue(user.getSeenAdvertisements().contains(ad));
        assertFalse(user.getClickedAdvertisements().contains(ad));
    }


    @Test
    public void testIgnoredAd_15MinutesPassedSinceView() {
        SocialMediaUser user = DroolsTestUtils.getBasicUser();
        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        user.getAdvertisementsToBeShown().add(ad);

        kieSession.insert(new AdvertisementViewEvent(new Date(), user, ad));

        SessionPseudoClock clock = kieSession.getSessionClock();
        clock.advanceTime(15, TimeUnit.MINUTES);
        kieSession.fireAllRules();

        assertFalse(user.getSeenAdvertisements().contains(ad));
        assertTrue(user.getIgnoredAdvertisements().contains(ad));
    }

}
