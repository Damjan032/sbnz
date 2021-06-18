package com.sbnz.adsys.service;

import com.sbnz.adsys.dto.BasicUserInfoDTO;
import com.sbnz.adsys.dto.CandidateDTO;
import com.sbnz.adsys.model.Candidate;
import com.sbnz.adsys.model.SocialMediaUser;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {

    public CandidateDTO toDTO(Candidate candidate) {
        return CandidateDTO.builder()
                .user(userInfo(candidate.getUser()))
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

    public BasicUserInfoDTO userInfo(SocialMediaUser user) {
        return BasicUserInfoDTO.builder()
                .firstName(user.getUser().getFirstName())
                .lastName(user.getUser().getLastName())
                .email(user.getUser().getEmail())
                .age(user.getAge())
                .city(user.getCity())
                .country(user.getCountry())
                .build();
    }
}
