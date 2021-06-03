package com.sbnz.adsys.unit;

import com.sbnz.adsys.model.*;
import com.sbnz.adsys.util.DroolsTestUtils;
import org.droolsassert.DroolsAssert;
import org.droolsassert.DroolsSession;
import org.droolsassert.TestRules;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.Assert.*;

@DroolsSession(resources = {
        "classpath*:/sbnz/integracija/Sample.drl"},
        ignoreRules = {"before", "after"},
        keepFactsHistory = false,
        logResources = true)
public class CalculateCoefficientRules {

    @Rule
    public DroolsAssert drools = new DroolsAssert();

    @Before
    public void init() {
        drools.setGlobal("COMPANY_COEFFICIENT", 1.);
        drools.setGlobal("CLICKED_ADS_COEFFICIENT", 1.);
        drools.setGlobal("IGNORED_ADS_COEFFICIENT", 1.);
        drools.setGlobal("LIKED_PAGES_COEFFICIENT", 1.);
        drools.setGlobal("HERD_COEFFICIENT", 1.);
    }

    @Test
    @TestRules(expected = "Candidate and Company Interaction Coefficient", ignore = "*")
    public void testCompanyInteraction_OnePositive() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        AdvertisementRequest request = DroolsTestUtils.getAdvertisementRequestToFitCandidate(candidate);
        String name = request.getAdvertisement().getAdvertiser().getName();

        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        ad.getAdvertiser().setName(name);
        candidate.getUser().getClickedAdvertisements().add(ad);

        drools.insertAndFire(candidate, request);
        assertEquals(1, candidate.getAdsByCompanyCoefficient(), 1e-5);
    }

    @Test
    @TestRules(expected = "Candidate and Company Interaction Coefficient", ignore = "*")
    public void testCompanyInteraction_OneNegative() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        AdvertisementRequest request = DroolsTestUtils.getAdvertisementRequestToFitCandidate(candidate);
        String name = request.getAdvertisement().getAdvertiser().getName();

        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        ad.getAdvertiser().setName(name);
        candidate.getUser().getIgnoredAdvertisements().add(ad);

        drools.insertAndFire(candidate, request);
        assertEquals(0, candidate.getAdsByCompanyCoefficient(), 1e-5);
    }

    @Test
    @TestRules(expected = "Candidate and Company Interaction Coefficient", ignore = "*")
    public void testCompanyInteraction_OneNegativeAndOneNegative() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        AdvertisementRequest request = DroolsTestUtils.getAdvertisementRequestToFitCandidate(candidate);
        String name = request.getAdvertisement().getAdvertiser().getName();

        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        ad.getAdvertiser().setName(name);
        candidate.getUser().getIgnoredAdvertisements().add(ad);

        Advertisement ad2 = DroolsTestUtils.getBasicAdvertisement();
        ad2.getAdvertiser().setName(name);
        candidate.getUser().getClickedAdvertisements().add(ad2);

        drools.insertAndFire(candidate, request);
        assertEquals(0.5, candidate.getAdsByCompanyCoefficient(), 1e-5);
    }

    @Test
    @TestRules(expected = "Calculate Clicked Ads Coefficient", ignore = "*")
    public void testClickedAds_OneToday() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        AdvertisementRequest request = DroolsTestUtils.getAdvertisementRequestToFitCandidate(candidate);
        request.getAdvertisement().setTags(DroolsTestUtils.createTags("sport", "football"));

        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        ad.setDatePublished(LocalDate.now());
        ad.setTags(DroolsTestUtils.createTags("sport", "basketball"));
        candidate.getUser().getClickedAdvertisements().add(ad);

        drools.insertAndFire(candidate, request);
        assertEquals(1, candidate.getClickedAdsCoefficient(), 1e-5);
    }

    @Test
    @TestRules(expected = "Calculate Clicked Ads Coefficient", ignore = "*")
    public void testClickedAds_TwoToday() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        AdvertisementRequest request = DroolsTestUtils.getAdvertisementRequestToFitCandidate(candidate);
        request.getAdvertisement().setTags(DroolsTestUtils.createTags("sport", "football"));

        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        ad.setDatePublished(LocalDate.now());
        ad.setTags(DroolsTestUtils.createTags("sport", "basketball"));
        candidate.getUser().getClickedAdvertisements().add(ad);

        Advertisement ad2 = DroolsTestUtils.getBasicAdvertisement();
        ad2.setDatePublished(LocalDate.now());
        ad2.setTags(DroolsTestUtils.createTags("sport", "football"));
        candidate.getUser().getClickedAdvertisements().add(ad2);

        drools.insertAndFire(candidate, request);
        assertEquals(3, candidate.getClickedAdsCoefficient(), 1e-5);
    }

    @Test
    @TestRules(expected = "Calculate Clicked Ads Coefficient", ignore = "*")
    public void testClickedAds_OneFiveDaysAgo() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        AdvertisementRequest request = DroolsTestUtils.getAdvertisementRequestToFitCandidate(candidate);
        request.getAdvertisement().setTags(DroolsTestUtils.createTags("sport", "football"));

        Advertisement ad = DroolsTestUtils.getBasicAdvertisement();
        ad.setDatePublished(request.getAdvertisement().getDatePublished().minusDays(5));
        ad.setTags(DroolsTestUtils.createTags("sport", "basketball"));
        candidate.getUser().getClickedAdvertisements().add(ad);

        drools.insertAndFire(candidate, request);
        assertEquals(0.2, candidate.getClickedAdsCoefficient(), 1e-5);
    }

    @Test
    @TestRules(expected = "Calculate Liked Pages Coefficient", ignore = "*")
    public void testLikedPages_OneWithSameTag() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        AdvertisementRequest request = DroolsTestUtils.getAdvertisementRequestToFitCandidate(candidate);

        request.getAdvertisement().setTags(DroolsTestUtils.createTags("sport", "football"));

        SocialMediaPage page = new SocialMediaPage();
        page.setCategoryKeywords(DroolsTestUtils.createTags("sport"));
        candidate.getUser().getLikedSocialMediaPages().add(page);

        drools.insertAndFire(candidate, request);
        assertEquals(1, candidate.getLikedPagesCoefficient(), 1e-5);
    }

    @Test
    @TestRules(expected = "Calculate Liked Pages Coefficient", ignore = "*")
    public void testLikedPages_TwoPagesThreeEqualTags() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        AdvertisementRequest request = DroolsTestUtils.getAdvertisementRequestToFitCandidate(candidate);

        request.getAdvertisement().setTags(DroolsTestUtils.createTags("sport", "football"));

        SocialMediaPage page = new SocialMediaPage();
        page.setCategoryKeywords(DroolsTestUtils.createTags("sport"));
        candidate.getUser().getLikedSocialMediaPages().add(page);

        SocialMediaPage page2 = new SocialMediaPage();
        page2.setCategoryKeywords(DroolsTestUtils.createTags("sport", "football"));
        candidate.getUser().getLikedSocialMediaPages().add(page2);

        drools.insertAndFire(candidate, request);
        assertEquals(3, candidate.getLikedPagesCoefficient(), 1e-5);
    }

    @Test
    @TestRules(expected = "Calculate Liked Pages Coefficient", ignore = "*")
    public void testLikedPages_ZeroEqualTags() {
        Candidate candidate = DroolsTestUtils.getBasicCandidate();
        AdvertisementRequest request = DroolsTestUtils.getAdvertisementRequestToFitCandidate(candidate);

        request.getAdvertisement().setTags(DroolsTestUtils.createTags("sport", "football"));

        SocialMediaPage page = new SocialMediaPage();
        page.setCategoryKeywords(DroolsTestUtils.createTags("something else", "if else"));
        candidate.getUser().getLikedSocialMediaPages().add(page);

        drools.insertAndFire(candidate, request);
        assertEquals(0, candidate.getLikedPagesCoefficient(), 1e-5);
    }


    @Test
    @TestRules(expected = "Calculate Herd Coefficient", ignore = "*")
    public void testHerdCoefficient_TwoCandidatesWithDifferentScores() {
        SocialMediaPage page = new SocialMediaPage();
        page.setUsersWhoLikeThePage(new LinkedList<>());
        page.setCategoryKeywords(DroolsTestUtils.createTags("football", "outdoors"));

        Candidate candidate1 = DroolsTestUtils.getBasicCandidate("cand1");
        candidate1.getUser().getLikedSocialMediaPages().add(page);
        page.getUsersWhoLikeThePage().add(candidate1.getUser());

        AdvertisementRequest request = DroolsTestUtils.getAdvertisementRequestToFitCandidate(candidate1);
        request.getAdvertisement().setTags(DroolsTestUtils.createTags("sport", "football"));

        Candidate candidate2 = DroolsTestUtils.getBasicCandidate("cand2");
        Advertisement ad2 = DroolsTestUtils.getBasicAdvertisement();
        ad2.setTags(DroolsTestUtils.createTags("sport", "football", "basketball"));
        candidate2.getUser().getClickedAdvertisements().add(ad2);
        candidate2.getUser().getLikedSocialMediaPages().add(page);
        page.getUsersWhoLikeThePage().add(candidate2.getUser());

        Candidate candidate3 = DroolsTestUtils.getBasicCandidate("cand3");
        candidate3.getUser().getLikedSocialMediaPages().add(page);
        page.getUsersWhoLikeThePage().add(candidate3.getUser());

        drools.insertAndFire(candidate1, candidate2, candidate3, request);
        double average = DroolsTestUtils.getAverage(candidate2.getInitialScore(), candidate3.getInitialScore());

        assertEquals(average, candidate1.getHerdCoefficient(), 1e-5);
    }

    @Test
    @TestRules(expected = "Calculate Herd Coefficient", ignore = "*")
    public void testHerdCoefficient_ZeroPeopleInHerd() {
        SocialMediaPage page = new SocialMediaPage();
        page.setUsersWhoLikeThePage(new LinkedList<>());
        page.setCategoryKeywords(DroolsTestUtils.createTags("football", "outdoors"));

        Candidate candidate1 = DroolsTestUtils.getBasicCandidate("cand1");
        candidate1.getUser().getLikedSocialMediaPages().add(page);
        page.getUsersWhoLikeThePage().add(candidate1.getUser());

        AdvertisementRequest request = DroolsTestUtils.getAdvertisementRequestToFitCandidate(candidate1);
        request.getAdvertisement().setTags(DroolsTestUtils.createTags("sport", "football"));

        drools.insertAndFire(candidate1, request);
        assertEquals(0, candidate1.getHerdCoefficient(), 1e-5);
    }


}