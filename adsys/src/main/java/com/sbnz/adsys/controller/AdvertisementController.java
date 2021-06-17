package com.sbnz.adsys.controller;

import com.sbnz.adsys.dto.AdvertisementDTO;
import com.sbnz.adsys.dto.AdvertiserDTO;
import com.sbnz.adsys.service.AdvertisementService;
import com.sbnz.adsys.service.AdvertiserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping("/toBeSeen/{id}")
    public ResponseEntity<List<AdvertisementDTO>> findToBeSeenByUser(@PathVariable("id") long id) {
        try {
            return ResponseEntity.ok(advertisementService.findToBeSeenByPatientId(id));
        } catch (Exception e) {
            return ResponseEntity.ok(new LinkedList<>());
        }
    }


}

