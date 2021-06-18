package com.sbnz.adsys.controller;

import com.sbnz.adsys.dto.AdvertisementRequestDTO;
import com.sbnz.adsys.dto.CandidateDTO;
import com.sbnz.adsys.dto.SocialMediaPageDTO;
import com.sbnz.adsys.service.AdvertisementRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.validation.beanvalidation.MethodValidationExcludeFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.Validation;
import java.util.List;

@RestController
@RequestMapping(value = "/api/requests", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdvertisementRequestController {

    @Autowired
    AdvertisementRequestService advertisementRequestService;

    @PostMapping
    public ResponseEntity<List<CandidateDTO>> submit(@Valid @RequestBody AdvertisementRequestDTO requestDTO) {
        return ResponseEntity.ok(advertisementRequestService.submit(requestDTO));
    }
}
