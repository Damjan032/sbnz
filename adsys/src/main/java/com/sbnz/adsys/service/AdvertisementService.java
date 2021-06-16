package com.sbnz.adsys.service;

import com.sbnz.adsys.dto.AdvertisementDTO;
import com.sbnz.adsys.model.Advertisement;
import com.sbnz.adsys.model.Tag;
import com.sbnz.adsys.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private AdvertiserService advertiserService;

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
