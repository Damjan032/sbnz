package com.sbnz.adsys.util;

import com.sbnz.adsys.model.*;
import org.checkerframework.checker.units.qual.A;
import org.drools.core.util.ClassUtilsTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DroolsTestUtils {

    public static Candidate getBasicCandidate() {
        Candidate candidate = new Candidate();
        SocialMediaUser user = new SocialMediaUser();
        user.setUser(new User());
        user.getUser().setFirstName("User");
        user.getUser().setLastName("Useric");
        user.setAge(18);
        user.setCity("Belgrade");
        user.setCountry("Serbia");

        user.setClickedAdvertisements(new LinkedList<>());
        user.setIgnoredAdvertisements(new LinkedList<>());
        user.setLikedSocialMediaPages(new LinkedList<>());

        candidate.setUser(user);
        return candidate;
    }

    public static Advertisement getBasicAdvertisement() {
        Advertiser advertiser = new Advertiser();
        advertiser.setAdvertisements(new LinkedList<>());
        advertiser.setName("Google");

        Advertisement ad = new Advertisement();
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
}
