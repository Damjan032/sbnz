package com.sbnz.adsys.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CandidateDTO {
    private SocialMediaUserDTO user;

    private boolean isAgeGroup, isGeographyGroup;
    private boolean isTargetGroup;
    private boolean isDisqualified;
    private boolean isAutomaticallyQualified;

    private double adsByCompanyCoefficient;
    private double clickedAdsCoefficient;
    private double ignoredAdsCoefficient;
    private double likedPagesCoefficient;

    private double herdCoefficient;

    private double initialScore;
    private double finalScore;
}
