package com.sbnz.adsys.service;

import com.sbnz.adsys.dto.AdvertisementDTO;
import com.sbnz.adsys.dto.SocialMediaUserDTO;
import com.sbnz.adsys.event.AdvertisementViewEvent;
import com.sbnz.adsys.exception.BadRequestException;
import com.sbnz.adsys.model.Advertisement;
import com.sbnz.adsys.model.SocialMediaUser;
import com.sbnz.adsys.model.Tag;
import com.sbnz.adsys.repository.AdvertisementRepository;
import javassist.tools.web.BadHttpRequest;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {
    
    @Autowired
    private KieService kieService;
    private static final String RECOMMENDATION_SESSION = "advertisement";

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private AdvertiserService advertiserService;
    
    @Autowired
    private SocialMediaUserService socialMediaUserService;

    @Autowired
    private TagService tagService;

    public Advertisement save(Advertisement advertisement) {
        return advertisementRepository.save(advertisement);
    }

    public AdvertisementDTO toDTO(Advertisement advertisement) {
        List<String> tags = advertisement.getTags().stream()
                .map(Tag::getValue)
                .collect(Collectors.toList());

        return AdvertisementDTO.builder()
                .id(advertisement.getId())
                .title(advertisement.getTitle())
                .content(advertisement.getContent())
                .targetUrl(advertisement.getTargetUrl())
                .tags(tags)
                .advertiser(advertiserService.toDTO(advertisement.getAdvertiser()))
                .build();
    }
    
    public boolean advertisementHasBeeSeen(long id, SocialMediaUserDTO socialMediaUserDTO){
        Optional<Advertisement> advertisement = this.advertisementRepository.findById(id);
        SocialMediaUser socialMediaUser = this.socialMediaUserService.toEntity(socialMediaUserDTO);
        if(!advertisement.isPresent())
            throw new BadRequestException("Wrong advertisement id");
        AdvertisementViewEvent advertisementViewEvent = new AdvertisementViewEvent(socialMediaUser, advertisement.get());
        KieSession kieSession = kieService.getSession(RECOMMENDATION_SESSION);
        kieSession.setGlobal("socialMediaUserService", this.socialMediaUserService);
        kieSession.insert(advertisementViewEvent);
        kieSession.fireAllRules();
        
        //TODO tred koji nekan N minuta provera da li se advertisement nalazi u listi kliknutih, ako ne onda ce nestati.
        return true;
    }

    public Advertisement toEntity(AdvertisementDTO dto) {
        List<Tag> tags = dto.getTags().stream()
                .map((tag) -> tagService.createOrFind(tag))
                .collect(Collectors.toList());

        return Advertisement.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .targetUrl(dto.getTargetUrl())
                .tags(tags)
                .advertiser(advertiserService.toEntity(dto.getAdvertiser()))
                .datePublished(LocalDate.now())
                .build();
    }
}
