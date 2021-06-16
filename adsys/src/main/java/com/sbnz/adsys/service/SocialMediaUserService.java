package com.sbnz.adsys.service;

import com.sbnz.adsys.dto.AdvertisementDTO;
import com.sbnz.adsys.dto.AdvertiserDTO;
import com.sbnz.adsys.dto.SocialMediaPageDTO;
import com.sbnz.adsys.dto.SocialMediaUserDTO;
import com.sbnz.adsys.model.Advertisement;
import com.sbnz.adsys.model.SocialMediaPage;
import com.sbnz.adsys.model.SocialMediaUser;
import com.sbnz.adsys.repository.SocialMediaUserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class SocialMediaUserService {

    @Autowired
    private SocialMediaUserRepository socialMediaUserRepository;

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private SocialMediaPageService pageDTO;

    public List<SocialMediaUserDTO> findAll() {
        return socialMediaUserRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<SocialMediaUser> findAllEntity() {
        return new ArrayList<>(socialMediaUserRepository.findAll());
    }

    @Transactional
    public void  removeAdvertisementFromSocialMediaUser(SocialMediaUser socialMediaUser, Advertisement advertisement){
        Optional<Advertisement> adToIgnore = socialMediaUser.getAdvertisementsToBeShown()
                .stream().filter(ad -> ad.getId().equals(advertisement.getId())).findFirst();
        if (adToIgnore.isPresent()){
            socialMediaUser.getAdvertisementsToBeShown().remove(adToIgnore.get());
            socialMediaUser.getIgnoredAdvertisements().add(adToIgnore.get());
        }
    }

    public void addAdToBeSeen(Advertisement ad, SocialMediaUser user) {
        System.out.println("Advertisement " + ad + " added to user's " + user + " to be viewed");
    }

    public void adSeenByUser(Advertisement ad, SocialMediaUser user){
        System.out.println("User " + user + " has seen the ad: " + ad);

        if (user.getAdvertisementsToBeShown().contains(ad)) {
            user.getAdvertisementsToBeShown().remove(ad);
            user.getSeenAdvertisements().add(ad);
        } else {
            // raise exception here
        }
    }

    public void adIgnoredByUser(Advertisement ad, SocialMediaUser user){
        System.out.println("User " + user + " has ignored the ad: " + ad);

        if (user.getSeenAdvertisements().contains(ad)) {
            user.getSeenAdvertisements().remove(ad);
            user.getIgnoredAdvertisements().add(ad);
        } else {
            // raise exception here
        }
    }

    public void adClickedByUser(Advertisement ad, SocialMediaUser user){
        System.out.println("User " + user + " has clicked the ad: " + ad);

        if (user.getSeenAdvertisements().contains(ad)) {
            user.getSeenAdvertisements().remove(ad);
            user.getClickedAdvertisements().add(ad);
        } else {
            // raise exception here
        }
    }

    public SocialMediaUserDTO toDTO(SocialMediaUser user) {

        List<AdvertisementDTO> toBeShown = user.getAdvertisementsToBeShown()
                .stream().map(advertisementService::toDTO)
                .collect(Collectors.toList());

        List<SocialMediaPageDTO> pages = user.getLikedSocialMediaPages()
                .stream().map(pageDTO::toDTO)
                .collect(Collectors.toList());

        return SocialMediaUserDTO.builder()
                .id(user.getId())
                .user(user.getUser())
                .age(user.getAge())
                .city(user.getCity())
                .country(user.getCountry())
                .advertisementsToBeShown(toBeShown)
                .likedSocialMediaPages(pages)
                .build();
    }

    public SocialMediaUser toEntity(SocialMediaUserDTO dto) {

        List<Advertisement> toBeShown = dto.getAdvertisementsToBeShown()
                .stream().map(advertisementService::toEntity)
                .collect(Collectors.toList());

        List<SocialMediaPage> pages = dto.getLikedSocialMediaPages()
                .stream().map(pageDTO::toEntity)
                .collect(Collectors.toList());

        return SocialMediaUser.builder()
                .id(dto.getId())
                .age(dto.getAge())
                .city(dto.getCity())
                .country(dto.getCountry())
                .advertisementsToBeShown(toBeShown)
                .likedSocialMediaPages(pages)
                .build();
    }
}
