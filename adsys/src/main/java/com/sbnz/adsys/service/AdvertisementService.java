package com.sbnz.adsys.service;

import com.sbnz.adsys.dto.AdvertisementDTO;
import com.sbnz.adsys.dto.AdvertisementEventDTO;
import com.sbnz.adsys.dto.SocialMediaUserDTO;
import com.sbnz.adsys.event.AdvertisementClickEvent;
import com.sbnz.adsys.event.AdvertisementViewEvent;
import com.sbnz.adsys.exception.BadRequestException;
import com.sbnz.adsys.model.Advertisement;
import com.sbnz.adsys.model.SocialMediaUser;
import com.sbnz.adsys.model.Tag;
import com.sbnz.adsys.repository.AdvertisementRepository;
import javassist.tools.web.BadHttpRequest;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {

    private static final Logger logger = LoggerFactory.getLogger(AdvertisementService.class);

    @Autowired
    private KieService kieService;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private AdvertiserService advertiserService;

    @Autowired
    private SocialMediaUserService socialMediaUserService;

    @Autowired
    private SocialMediaUserService userService;

    @Autowired
    private TagService tagService;

    private static final String RECOMMENDATION_SESSION = "advertisement";


    public Advertisement save(Advertisement advertisement) {
        return advertisementRepository.save(advertisement);
    }

    public List<AdvertisementDTO> findToBeSeenByUserId(long id) {
        return userService.findById(id)
                .getAdvertisementsToBeShown();
    }

    public void advertisementHasBeenSeen(AdvertisementEventDTO eventDTO) throws BadRequestException {
        Optional<Advertisement> advertisement = this.advertisementRepository.findById(eventDTO.getAdvertisementId());
        Optional<SocialMediaUser> socialMediaUser = this.socialMediaUserService.findByIdEntity(eventDTO.getUserId());

        if (!advertisement.isPresent() || !socialMediaUser.isPresent())
            throw new BadRequestException("Wrong advertisement id");

        AdvertisementViewEvent viewEvent = new AdvertisementViewEvent(socialMediaUser.get(), advertisement.get());
        logger.info("New View event by user {} for ad by {}", socialMediaUser.get().fullName(),
                advertisement.get().getAdvertiser().getName());

        KieSession session = kieService.getEventSession();
        session.insert(viewEvent);
//        session.fireAllRules();

        new Thread(() -> fireRulesAfterDelay(session, 2000)).start();
    }

    public void advertisementHasBeenClicked(AdvertisementEventDTO eventDTO) throws BadRequestException {
        Optional<Advertisement> advertisement = this.advertisementRepository.findById(eventDTO.getAdvertisementId());
        Optional<SocialMediaUser> socialMediaUser = this.socialMediaUserService.findByIdEntity(eventDTO.getUserId());

        if (!advertisement.isPresent() || !socialMediaUser.isPresent())
            throw new BadRequestException("Wrong advertisement id");

        AdvertisementClickEvent clickEvent = new AdvertisementClickEvent(socialMediaUser.get(), advertisement.get());
        logger.info("New Click event by user {} for ad by {}", socialMediaUser.get().fullName(),
                advertisement.get().getAdvertiser().getName());

        KieSession session = kieService.getEventSession();
        session.insert(clickEvent);
        session.fireAllRules();
    }

    private void fireRulesAfterDelay(KieSession session, int delay) {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        session.fireAllRules();
                        System.out.println("Executed rules after delay " + delay);
                    }
                },
                delay
        );
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
