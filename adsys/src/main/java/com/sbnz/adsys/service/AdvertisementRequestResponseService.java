package com.sbnz.adsys.service;

import com.sbnz.adsys.dto.AdvertisementRequestResponseDTO;
import com.sbnz.adsys.model.AdvertisementRequestResponse;
import com.sbnz.adsys.repository.AdvertisementRequestResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisementRequestResponseService {

    @Autowired
    private AdvertisementRequestResponseRepository responseRepository;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private AdvertisementService advertisementService;

    public List<AdvertisementRequestResponseDTO> findAll() {
        return responseRepository.findAll()
                .stream()
                .map(this::toDTO)
                .sorted(Comparator.comparingLong(resp -> - resp.getDate().toEpochSecond(ZoneOffset.UTC)))
                .collect(Collectors.toList());
    }

    public AdvertisementRequestResponseDTO save(AdvertisementRequestResponse response) {
        return toDTO(responseRepository.save(response));
    }

    public AdvertisementRequestResponseDTO toDTO(AdvertisementRequestResponse response) {
        return AdvertisementRequestResponseDTO.builder()
                .id(response.getId())
                .candidates(response.getCandidates()
                        .stream()
                        .map(candidateService::toDTO)
                        .collect(Collectors.toList()))
                .advertisement(advertisementService.toDTO(response.getAdvertisement()))
                .date(response.getDate())
                .build();
    }
}
