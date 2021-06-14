package com.sbnz.adsys.service;

import com.sbnz.adsys.dto.TagDTO;
import com.sbnz.adsys.model.Tag;
import com.sbnz.adsys.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag createOrFind(TagDTO tag) {
        Optional<Tag> optional = tagRepository.findTagByValue(tag.getValue());
        return optional.orElseGet(() -> tagRepository.save(toEntity(tag)));
    }

    public Tag createOrFind(String value) {
        Optional<Tag> optional = tagRepository.findTagByValue(value);
        return optional.orElseGet(() -> tagRepository.save(new Tag(value)));
    }

    public TagDTO toDTO(Tag tag) {
        return TagDTO.builder()
                .id(tag.getId())
                .value(tag.getValue())
                .build();
    }

    public Tag toEntity(TagDTO dto) {
        return Tag.builder()
                .id(dto.getId())
                .value(dto.getValue())
                .build();
    }
}
