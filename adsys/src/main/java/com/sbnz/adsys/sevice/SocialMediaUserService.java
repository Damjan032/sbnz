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
}
