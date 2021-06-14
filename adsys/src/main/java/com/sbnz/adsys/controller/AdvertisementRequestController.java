package com.sbnz.adsys.controller;

import com.sbnz.adsys.dto.AdvertisementRequestDTO;
import com.sbnz.adsys.service.AdvertisementRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/advertisementRequest", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdvertisementRequestController {

    @Autowired
    AdvertisementRequestService advertisementRequestService;

    @PostMapping
    public ResponseEntity<Void> submit(@RequestBody AdvertisementRequestDTO requestDTO) {
        advertisementRequestService.submit(requestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
