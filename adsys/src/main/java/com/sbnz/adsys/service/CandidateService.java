package com.sbnz.adsys.service;

import com.sbnz.adsys.dto.CandidateDTO;
import com.sbnz.adsys.model.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    @Autowired
    private SocialMediaUserService userService;

    public CandidateDTO toDTO(Candidate candidate) {
        return CandidateDTO.builder()
                .user(userService.toDTO(candidate.getUser()))
                .isAgeGroup(candidate.isAgeGroup())
                .isGeographyGroup(candidate.isGeographyGroup())
                .isTargetGroup(candidate.isTargetGroup())
                .isDisqualified(candidate.isDisqualified())
                .isAutomaticallyQualified(candidate.isAutomaticallyQualified())
                .clickedAdsCoefficient(candidate.getClickedAdsCoefficient())
                .adsByCompanyCoefficient(candidate.getAdsByCompanyCoefficient())
                .ignoredAdsCoefficient(candidate.getIgnoredAdsCoefficient())
                .likedPagesCoefficient(candidate.getLikedPagesCoefficient())
                .herdCoefficient(candidate.getHerdCoefficient())
                .initialScore(candidate.getInitialScore())
                .finalScore(candidate.getFinalScore())
                .build();
    }
}
