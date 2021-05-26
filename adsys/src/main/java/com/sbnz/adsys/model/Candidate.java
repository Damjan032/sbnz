package com.sbnz.adsys.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Candidate {

    private SocialMediaUser user;

    private boolean isAgeGroup, isGeographyGroup;
    private boolean isTargetGroup;
    private boolean isDisqualified;
    private boolean isAutomaticallyQualified;

    private double clickedAdsCoefficient = 0;
    private double ignoredAdsCoefficient = 0;
    private double likedPagesCoefficient = 0;

    public double getFinalScore() {
        if (isAutomaticallyQualified) {
            return Double.POSITIVE_INFINITY;
        }

        return clickedAdsCoefficient + 2 * ignoredAdsCoefficient + likedPagesCoefficient;
    }
}
