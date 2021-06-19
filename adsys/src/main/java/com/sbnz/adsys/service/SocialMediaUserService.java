package com.sbnz.adsys.service;

import com.google.common.collect.Sets;
import com.sbnz.adsys.dto.AdvertisementDTO;
import com.sbnz.adsys.dto.AdvertiserDTO;
import com.sbnz.adsys.dto.SocialMediaPageDTO;
import com.sbnz.adsys.dto.SocialMediaUserDTO;
import com.sbnz.adsys.exception.BadRequestException;
import com.sbnz.adsys.model.Advertisement;
import com.sbnz.adsys.model.SocialMediaPage;
import com.sbnz.adsys.model.SocialMediaUser;
import com.sbnz.adsys.repository.AdvertisementRepository;
import com.sbnz.adsys.repository.SocialMediaUserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class SocialMediaUserService {

    private static final Logger logger = LoggerFactory.getLogger(SocialMediaUserService.class);

    @Autowired
    private AdvertisementRepository advertisementRepository;
    
    @Autowired
    private SocialMediaUserRepository socialMediaUserRepository;

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private SocialMediaPageService pageDTO;

    public SocialMediaUser save(SocialMediaUser user) {
        return socialMediaUserRepository.save(user);
    }

    public List<SocialMediaUserDTO> findAll() {
        return socialMediaUserRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public SocialMediaUserDTO findById(long id)  {
        return toDTO(socialMediaUserRepository.findById(id).get());
    }

    public Optional<SocialMediaUser> findByIdEntity(long id)  {
        return socialMediaUserRepository.findById(id);
    }

    public List<SocialMediaUser> findAllEntity() {
        return new ArrayList<>(socialMediaUserRepository.findAll());
    }

    @Transactional
    public void removeAdvertisementFromSocialMediaUser(SocialMediaUser user, Advertisement ad) throws BadRequestException {
        logger.info("Remove by {} added to user's {} SEEN ads", ad.getAdvertiser().getName(), user.fullName());
        Optional<SocialMediaUser> socialMediaUser = this.socialMediaUserRepository.findById(user.getId());
        Optional<Advertisement> advertisement = this.advertisementRepository.findById(ad.getId());
        if(!socialMediaUser.isPresent() || !advertisement.isPresent()) throw new BadRequestException("Wrong user id");
        
        Optional<Advertisement> adToIgnore = socialMediaUser.get().getAdvertisementsToBeShown()
                .stream().filter(adToIgnored -> adToIgnored.getId().equals(advertisement.get().getId())).findFirst();
        if (adToIgnore.isPresent()) {
            advertisement.get().getSocialMediaUsersIgnored().add(socialMediaUser.get());
            advertisement.get().getSocialMediaUsersToBeShow().remove(socialMediaUser.get());
            
            socialMediaUser.get().getAdvertisementsToBeShown().remove(adToIgnore.get());
            socialMediaUser.get().getIgnoredAdvertisements().add(adToIgnore.get());
        }
    }

    @Transactional
    public void adSeenByUser(Advertisement ad, SocialMediaUser user) throws BadRequestException {
        logger.info("Ad {} by {} added to user's {} SEEN ads", ad.getContent(),
                ad.getAdvertiser().getName(), user.fullName());
        Optional<SocialMediaUser> socialMediaUser = this.socialMediaUserRepository.findById(user.getId());
        Optional<Advertisement> advertisement = this.advertisementRepository.findById(ad.getId());
        if(!socialMediaUser.isPresent() || !advertisement.isPresent()) throw new BadRequestException("Wrong user id");
        
        if (socialMediaUser.get().getAdvertisementsToBeShown().contains(ad)) {
            if(advertisement.get().getSocialMediaUsersSeen()==null) advertisement.get().setSocialMediaUsersSeen(new LinkedList<>());
            if(socialMediaUser.get().getSeenAdvertisements()==null) socialMediaUser.get().setSeenAdvertisements(new LinkedList<>());
            
            advertisement.get().getSocialMediaUsersSeen().add(socialMediaUser.get());
            advertisement.get().getSocialMediaUsersToBeShow().remove(socialMediaUser.get());
            socialMediaUser.get().getAdvertisementsToBeShown().remove(advertisement.get());
            socialMediaUser.get().getSeenAdvertisements().add(advertisement.get());
           
        }
    }

    @Transactional
    public void adIgnoredByUser(Advertisement ad, SocialMediaUser user) throws BadRequestException {
        logger.info("Ad {} by {} added to user's {} IGNORED ads", ad.getContent(),
                ad.getAdvertiser().getName(), user.fullName());
        Optional<SocialMediaUser> socialMediaUser = this.socialMediaUserRepository.findById(user.getId());
        Optional<Advertisement> advertisement = this.advertisementRepository.findById(ad.getId());
        if(!socialMediaUser.isPresent() || !advertisement.isPresent()) throw new BadRequestException("Wrong user id");
        
        if(!socialMediaUser.get().getIgnoredAdvertisements().contains(advertisement.get()))
            advertisement.get().getSocialMediaUsersIgnored().add(socialMediaUser.get());
            socialMediaUser.get().getIgnoredAdvertisements().add(advertisement.get());
            
        if (socialMediaUser.get().getAdvertisementsToBeShown().contains(advertisement.get())) {
            advertisement.get().getSocialMediaUsersToBeShow().add(socialMediaUser.get());
            socialMediaUser.get().getAdvertisementsToBeShown().add(advertisement.get());
        }
        if (socialMediaUser.get().getSeenAdvertisements().contains(advertisement.get())) {
            advertisement.get().getSocialMediaUsersSeen().remove(socialMediaUser.get());
            socialMediaUser.get().getSeenAdvertisements().remove(ad);
        }
    }

    public void adClickedByUser(Advertisement ad, SocialMediaUser user) throws BadRequestException {
        logger.info("Ad {} by {} added to user's {} CLICKED ads", ad.getContent(),
                ad.getAdvertiser().getName(), user.fullName());
        Optional<SocialMediaUser> socialMediaUser = this.socialMediaUserRepository.findById(user.getId());
        Optional<Advertisement> advertisement = this.advertisementRepository.findById(ad.getId());
        if(!socialMediaUser.isPresent() || !advertisement.isPresent()) throw new BadRequestException("Wrong user id");

        if (socialMediaUser.get().getSeenAdvertisements().contains(advertisement.get())) {
            if(advertisement.get().getSocialMediaUsersClicked()==null) advertisement.get().setSocialMediaUsersClicked(new LinkedList<>());
            if(socialMediaUser.get().getClickedAdvertisements()==null) advertisement.get().setSocialMediaUsersClicked(new LinkedList<>());
            
            advertisement.get().getSocialMediaUsersSeen().remove(socialMediaUser.get());
            advertisement.get().getSocialMediaUsersClicked().add(socialMediaUser.get());
            
            socialMediaUser.get().getSeenAdvertisements().remove(advertisement.get());
            socialMediaUser.get().getClickedAdvertisements().add(advertisement.get());
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
