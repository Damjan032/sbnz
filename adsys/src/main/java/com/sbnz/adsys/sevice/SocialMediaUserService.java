package com.sbnz.adsys.sevice;

import com.sbnz.adsys.model.Advertisement;
import com.sbnz.adsys.model.SocialMediaUser;
import com.sbnz.adsys.repository.SocialMediaUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SocialMediaUserService {
    private SocialMediaUserRepository socialMediaUserRepository;

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
}
