package com.sbnz.adsys.sevice;

import com.sbnz.adsys.dto.AdvertisementDTO;
import com.sbnz.adsys.dto.TagDTO;
import com.sbnz.adsys.model.Advertisement;
import com.sbnz.adsys.model.Advertiser;
import com.sbnz.adsys.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {

    @Autowired
    AdvertiserService advertiserService;

    public AdvertisementDTO toDTO(Advertisement advertisement) {
        List<TagDTO> tags = advertisement.getTags().stream()
                .map((tag) -> new TagDTO(tag.getId(), tag.getTag()))
                .collect(Collectors.toList());

        return new AdvertisementDTO(advertisement.getId(), advertisement.getTitle(), advertisement.getContent(),
                advertisement.getTargetUrl(), tags, advertisement.getBudget(),
                advertiserService.toDTO(advertisement.getAdvertiser()));
    }

    public Advertisement toEntity(AdvertisementDTO advertisementDTO) {
        List<Tag> tags = advertisementDTO.getTags().stream()
                .map((tag) -> new Tag(tag.getId(), tag.getTag()))
                .collect(Collectors.toList());

        Advertiser advertiser = advertiserService.toEntity(advertisementDTO.getAdvertiser());

        return new Advertisement(advertisementDTO.getId(), advertisementDTO.getTitle(), advertisementDTO.getContent(),
                advertisementDTO.getTargetUrl(), tags, advertisementDTO.getBudget(), null,
                advertiser, LocalDate.now());
    }
}
