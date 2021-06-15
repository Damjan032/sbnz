package com.sbnz.adsys.service;

import com.sbnz.adsys.dto.AdvertisementDTO;
import com.sbnz.adsys.dto.SocialMediaPageDTO;
import com.sbnz.adsys.model.Advertisement;
import com.sbnz.adsys.model.SocialMediaPage;
import com.sbnz.adsys.model.Tag;
import com.sbnz.adsys.repository.SocialMediaPageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SocialMediaPageService {

    @Autowired
    private final SocialMediaPageRepository pageRepository;

    @Autowired
    private final TagService tagService;

    public List<SocialMediaPageDTO> findAll() {
        return pageRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public SocialMediaPageDTO toDTO(SocialMediaPage page) {
        List<String> keywords = page.getCategoryKeywords().stream()
                .map(Tag::getValue)
                .collect(Collectors.toList());

        return SocialMediaPageDTO.builder()
                .id(page.getId())
                .picture(page.getPicture())
                .name(page.getName())
                .categoryKeywords(keywords)
                .build();
    }

    public SocialMediaPage toEntity(SocialMediaPageDTO dto) {
        List<Tag> tags = dto.getCategoryKeywords().stream()
                .map(tagService::createOrFind)
                .collect(Collectors.toList());

        return SocialMediaPage.builder()
                .id(dto.getId())
                .name(dto.getName())
                .picture(dto.getPicture())
                .categoryKeywords(tags)
                .build();
    }

}
