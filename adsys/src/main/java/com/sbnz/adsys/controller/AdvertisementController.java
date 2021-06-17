package com.sbnz.adsys.controller;

import com.sbnz.adsys.dto.AdvertisementRequestDTO;
import com.sbnz.adsys.dto.CandidateDTO;
import com.sbnz.adsys.dto.SocialMediaUserDTO;
import com.sbnz.adsys.dto.UserDto;
import com.sbnz.adsys.service.AdvertisementService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping(value = "/api/advertisement", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdvertisementController {
    
    @Autowired
    private AdvertisementService advertisementService;
    
    @PostMapping("/seenAdvertisement/{id}")
    public ResponseEntity<Boolean> adHasBeenSeen(@PathVariable long id, @RequestBody SocialMediaUserDTO socialMediaUserDTO) {
        System.out.println("AdHasBeenSeen");
        return ResponseEntity.ok(advertisementService.advertisementHasBeeSeen(id, socialMediaUserDTO));
    }
}
