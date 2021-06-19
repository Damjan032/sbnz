package com.sbnz.adsys.unit;

import com.sbnz.adsys.event.AdvertisementClickEvent;
import com.sbnz.adsys.event.AdvertisementIgnoredEvent;
import com.sbnz.adsys.event.AdvertisementViewEvent;
import com.sbnz.adsys.exception.BadRequestException;
import com.sbnz.adsys.model.Advertisement;
import com.sbnz.adsys.model.SocialMediaUser;
import com.sbnz.adsys.service.SocialMediaUserService;
import com.sbnz.adsys.util.DroolsTestUtils;

import org.drools.core.ClockType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.time.SessionPseudoClock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdvertisementEventsCep {
    private SocialMediaUserService socialMediaUserService;
    
    private KieSession kieSession;

    @Before
    public void init() throws BadRequestException {
        KieServices ks = KieServices.Factory.get();
        KieContainer kc = ks.newKieClasspathContainer();
        socialMediaUserService = mock(SocialMediaUserService.class);
        doNothing().when(socialMediaUserService).adIgnoredByUser(any(Advertisement.class), any(SocialMediaUser.class));
        doNothing().when(socialMediaUserService).adSeenByUser(any(Advertisement.class), any(SocialMediaUser.class));
        doNothing().when(socialMediaUserService).adClickedByUser(any(Advertisement.class), any(SocialMediaUser.class));
        KieSessionConfiguration ksconf = ks.newKieSessionConfiguration();
        ksconf.setOption(ClockTypeOption.get(ClockType.PSEUDO_CLOCK.getId()));
        kieSession = kc.newKieSession("advertisementEventsCep-session", ksconf);
        kieSession.setGlobal("socialMediaUserService", socialMediaUserService);
    }

    @Test
    public void testSeenAd_UserHasSeenTheAd() throws BadRequestException {
        SocialMediaUser user = DroolsTestUtils.getBasicUser();
        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        user.getAdvertisementsToBeShown().add(ad);
        AdvertisementViewEvent event = new AdvertisementViewEvent(user, ad);
        kieSession.insert(event);
        kieSession.fireAllRules();
        verify(socialMediaUserService,
                Mockito.times(1)).adSeenByUser(ad, user);
    }

    @Test
    public void testClickedAd_AfterSeeing() throws BadRequestException {
        SocialMediaUser user = DroolsTestUtils.getBasicUser();
        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        user.getAdvertisementsToBeShown().add(ad);

        kieSession.insert(new AdvertisementViewEvent( user, ad));
        
        kieSession.insert(new AdvertisementClickEvent( user, ad));
        kieSession.fireAllRules();
        verify(socialMediaUserService,
                Mockito.times(1)).adSeenByUser(ad, user);
        verify(socialMediaUserService,
                Mockito.times(1)).adClickedByUser(ad, user);
    }

    @Test
    public void testClickedAd_ElevenMinuteAfterSeeing_ClickNotRegistered() throws BadRequestException {
        SocialMediaUser user = DroolsTestUtils.getBasicUser();
        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        user.getAdvertisementsToBeShown().add(ad);
        SessionPseudoClock clock = kieSession.getSessionClock();
        kieSession.insert(new AdvertisementViewEvent(user, ad));
        kieSession.fireAllRules();
        clock.advanceTime(15, TimeUnit.MINUTES);
       /* kieSession.insert(new AdvertisementClickEvent(user, ad));*/
        kieSession.fireAllRules();
        verify(socialMediaUserService,
                Mockito.times(1)).adSeenByUser(ad, user);
        verify(socialMediaUserService,
                Mockito.times(0)).adClickedByUser(ad, user);
    }
    
    @Test
    public void testAdWasClickedForMoreThen20TimesButNotInstant() throws BadRequestException {
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
        verify(socialMediaUserService,
                Mockito.times(0)).adIgnoredByUser(ad, user);
    }
    
    @Test
    public void testAdWasClickedForLessThan10Times() throws BadRequestException {
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
        verify(socialMediaUserService,
                Mockito.times(0)).adIgnoredByUser(ad, user);
        assertFalse(user.getIgnoredAdvertisements().contains(ad));
    }
    
    @Test
    public void testIgnoredAdWasViewedForMoreThan10TimesInTheLast10Min() throws BadRequestException {
        SocialMediaUser user = DroolsTestUtils.getBasicUser();
        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        user.getAdvertisementsToBeShown().add(ad);
        for (int i = 0; i < 34; ++i) {
            kieSession.insert(new AdvertisementClickEvent(user, ad));
            kieSession.fireAllRules();
        }

        verify(socialMediaUserService, atLeast(1)).adIgnoredByUser(ad, user);
    }

}
