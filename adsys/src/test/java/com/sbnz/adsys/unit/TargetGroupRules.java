package com.sbnz.adsys.unit;

import com.sbnz.adsys.model.Advertisement;
import com.sbnz.adsys.model.AdvertisementRequest;
import com.sbnz.adsys.model.Candidate;
import com.sbnz.adsys.util.DroolsTestUtils;
import org.droolsassert.DroolsAssert;
import org.droolsassert.DroolsSession;
import org.droolsassert.TestRules;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@DroolsSession(resources = {
        "classpath*:/sbnz/integracija/Sample.drl"},
        ignoreRules = {"before", "after"},
        keepFactsHistory = false,
        logResources = true)
public class TargetGroupRules {

    @Rule
    public DroolsAssert drools = new DroolsAssert();

    @Before
    public void init() {
        drools.setGlobal("COMPANY_COEFFICIENT", 1.0);
        drools.setGlobal("CLICKED_ADS_COEFFICIENT", 1.0);
        drools.setGlobal("IGNORED_ADS_COEFFICIENT", 1.0);
        drools.setGlobal("LIKED_PAGES_COEFFICIENT", 1.0);
        drools.setGlobal("HERD_COEFFICIENT", 1.0);
    }

    @Test
    @TestRules(expected = "Target Group Age", ignore = "*")
    public void testAgeGroupRule_AgeBetweenMinAndMax() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        candidate.getUser().setAge(25);

        AdvertisementRequest advertisementRequest = DroolsTestUtils.getBasicAdvertisementRequest();
        advertisementRequest.setMinAge(24);
        advertisementRequest.setMaxAge(25);

        drools.insertAndFire(candidate, advertisementRequest);
        assertTrue(drools.getObject(Candidate.class).isAgeGroup());
    }

    @Test
    @TestRules(expected = "Target Group Age", ignore = "*")
    public void testAgeGroupRule_AgeOutsideMinAndMax() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        candidate.getUser().setAge(26);

        AdvertisementRequest advertisementRequest = DroolsTestUtils.getBasicAdvertisementRequest();
        advertisementRequest.setMinAge(24);
        advertisementRequest.setMaxAge(25);

        drools.insertAndFire(candidate, advertisementRequest);
        assertFalse(drools.getObject(Candidate.class).isAgeGroup());
    }

    @Test
    @TestRules(expected = "Target Group Geography", ignore = "*")
    public void testGeographyGroupRule_LocationSame() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        candidate.getUser().setCountry("Serbia");

        AdvertisementRequest advertisementRequest = DroolsTestUtils.getBasicAdvertisementRequest();
        advertisementRequest.setGeographicLocation("Serbia");

        drools.insertAndFire(candidate, advertisementRequest);
        assertTrue(drools.getObject(Candidate.class).isGeographyGroup());
    }

    @Test
    @TestRules(expected = "Target Group Geography", ignore = "*")
    public void testGeographyGroupRule_LocationNotSame() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        candidate.getUser().setCountry("Serbia");

        AdvertisementRequest advertisementRequest = DroolsTestUtils.getBasicAdvertisementRequest();
        advertisementRequest.setGeographicLocation("Germany");

        drools.insertAndFire(candidate, advertisementRequest);
        assertFalse(drools.getObject(Candidate.class).isGeographyGroup());
    }

    @Test
    @TestRules(expected = "Is Target Group", ignore = "*")
    public void testIsTargetGroup_AllRequirementsSatisfied() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        candidate.getUser().setCountry("Serbia");
        candidate.getUser().setAge(25);

        AdvertisementRequest advertisementRequest = DroolsTestUtils.getBasicAdvertisementRequest();
        advertisementRequest.setGeographicLocation("Serbia");
        advertisementRequest.setMinAge(20);
        advertisementRequest.setMaxAge(25);

        drools.insertAndFire(candidate, advertisementRequest);
        assertTrue(drools.getObject(Candidate.class).isTargetGroup());
    }

    @Test
    @TestRules(expected = "Is Target Group", ignore = "*")
    public void testIsTargetGroup_RequirementsNotSatisfied() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        candidate.getUser().setCountry("Serbia");
        candidate.getUser().setAge(99);

        AdvertisementRequest advertisementRequest = DroolsTestUtils.getBasicAdvertisementRequest();
        advertisementRequest.setGeographicLocation("Germany");
        advertisementRequest.setMinAge(20);
        advertisementRequest.setMaxAge(25);

        drools.insertAndFire(candidate, advertisementRequest);
        assertFalse(drools.getObject(Candidate.class).isTargetGroup());
    }

    @Test
    @TestRules(expected = "5+ Ads Clicked on this Month by the Same Company", ignore = "*")
    public void testFiveAdsClickedByCompany_CandidateSatisfiesRequirements() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        AdvertisementRequest request = DroolsTestUtils.getAdvertisementRequestToFitCandidate(candidate);
        String name = request.getAdvertisement().getAdvertiser().getName();

        for (int i = 0; i < 5; i++) {
            Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
            ad.getAdvertiser().setName(name);
            candidate.getUser().getClickedAdvertisements().add(ad);
        }

        drools.insertAndFire(candidate, request);
        assertTrue(drools.getObject(Candidate.class).isAutomaticallyQualified());
    }

    @Test
    @TestRules(expected = "5+ Ads Clicked on this Month by the Same Company", ignore = "*")
    public void testFiveAdsClickedByCompany_AdsOlderThanOneMonth() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        AdvertisementRequest request = DroolsTestUtils.getAdvertisementRequestToFitCandidate(candidate);
        String name = request.getAdvertisement().getAdvertiser().getName();

        for (int i = 0; i < 5; i++) {
            Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
            ad.getAdvertiser().setName(name);
            ad.setDatePublished(LocalDate.now().minusMonths(2));
            candidate.getUser().getClickedAdvertisements().add(ad);
        }

        drools.insertAndFire(candidate, request);
        assertFalse(drools.getObject(Candidate.class).isAutomaticallyQualified());
    }

    @Test
    @TestRules(expected = "5+ Ads Clicked on this Month by the Same Company", ignore = "*")
    public void testFiveAdsClickedByCompany_Only4Ads() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        AdvertisementRequest request = DroolsTestUtils.getAdvertisementRequestToFitCandidate(candidate);
        String name = request.getAdvertisement().getAdvertiser().getName();

        for (int i = 0; i < 4; i++) {
            Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
            ad.getAdvertiser().setName(name);
            candidate.getUser().getClickedAdvertisements().add(ad);
        }

        drools.insertAndFire(candidate, request);
        assertFalse(drools.getObject(Candidate.class).isAutomaticallyQualified());
    }

    @Test
    @TestRules(expected = "5+ Ads Ignored this Month by the Same Company", ignore = "*")
    public void testFiveAdsIgnoredByCompany_CandidateSatisfiesRequirements() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        AdvertisementRequest request = DroolsTestUtils.getAdvertisementRequestToFitCandidate(candidate);
        String name = request.getAdvertisement().getAdvertiser().getName();

        for (int i = 0; i < 5; i++) {
            Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
            ad.getAdvertiser().setName(name);
            candidate.getUser().getIgnoredAdvertisements().add(ad);
        }

        drools.insertAndFire(candidate, request);
        assertTrue(drools.getObject(Candidate.class).isDisqualified());
    }

    @Test
    @TestRules(expected = "5+ Ads Ignored this Month by the Same Company", ignore = "*")
    public void testFiveAdsIgnoredByCompany_AdsOlderThanOneMonth() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        AdvertisementRequest request = DroolsTestUtils.getAdvertisementRequestToFitCandidate(candidate);
        String name = request.getAdvertisement().getAdvertiser().getName();

        for (int i = 0; i < 5; i++) {
            Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
            ad.getAdvertiser().setName(name);
            ad.setDatePublished(LocalDate.now().minusMonths(2));
            candidate.getUser().getIgnoredAdvertisements().add(ad);
        }

        drools.insertAndFire(candidate, request);
        assertFalse(drools.getObject(Candidate.class).isDisqualified());
    }

    @Test
    @TestRules(expected = "5+ Ads Ignored this Month by the Same Company", ignore = "*")
    public void testFiveAdsIgnoredByCompany_Only4Ads() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        AdvertisementRequest request = DroolsTestUtils.getAdvertisementRequestToFitCandidate(candidate);
        String name = request.getAdvertisement().getAdvertiser().getName();

        for (int i = 0; i < 4; i++) {
            Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
            ad.getAdvertiser().setName(name);
            candidate.getUser().getIgnoredAdvertisements().add(ad);
        }

        drools.insertAndFire(candidate, request);
        assertFalse(drools.getObject(Candidate.class).isDisqualified());
    }
}