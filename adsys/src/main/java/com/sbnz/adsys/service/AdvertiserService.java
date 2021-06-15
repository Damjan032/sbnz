package com.sbnz.adsys.service;

import com.sbnz.adsys.dto.AdvertiserDTO;
import com.sbnz.adsys.model.Advertiser;
import com.sbnz.adsys.repository.AdvertiserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertiserService {

    @Autowired
    private AdvertiserRepository advertiserRepository;

    public List<AdvertiserDTO> get() {
        return advertiserRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public AdvertiserDTO getById(Long id) {
        return toDTO(advertiserRepository.getOne(id));
    }

    public AdvertiserDTO create(AdvertiserDTO advertiserDTO) {
        Advertiser advertiser = advertiserRepository.save(toEntity(advertiserDTO));
        return toDTO(advertiser);
    }

    public AdvertiserDTO toDTO(Advertiser advertiser) {
        return AdvertiserDTO.builder()
                .id(advertiser.getId())
                .name(advertiser.getName())
                .picture(advertiser.getPicture())
                .build();
    }

    public Advertiser toEntity(AdvertiserDTO advertiserDTO) {
        return Advertiser.builder()
                .id(advertiserDTO.getId())
                .name(advertiserDTO.getName())
                .picture(advertiserDTO.getPicture())
                .build();
    }

}
