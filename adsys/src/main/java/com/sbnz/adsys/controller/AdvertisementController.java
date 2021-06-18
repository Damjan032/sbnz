package com.sbnz.adsys.controller;
import com.sbnz.adsys.dto.AdvertisementDTO;
import com.sbnz.adsys.dto.AdvertisementEventDTO;
import com.sbnz.adsys.exception.BadRequestException;
import com.sbnz.adsys.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.LinkedList;

@RestController
@RequestMapping(value = "/api/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping("/toBeSeen/{id}")
    public ResponseEntity<List<AdvertisementDTO>> findToBeSeenByUser(@PathVariable("id") long id) {
        try {
            return ResponseEntity.ok(advertisementService.findToBeSeenByUserId(id));
        } catch (Exception e) {
            return ResponseEntity.ok(new LinkedList<>());
        }
    }

    @PostMapping("/seen")
    public ResponseEntity<Void> adHasBeenSeen(@Valid @RequestBody AdvertisementEventDTO dto) {
        try {
            advertisementService.advertisementHasBeenSeen(dto);
            return ResponseEntity.noContent().build();
        } catch (BadRequestException e) {
            System.out.println("view failed");
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/clicked")
    public ResponseEntity<Void> adHasBeenClicked(@Valid @RequestBody AdvertisementEventDTO dto) {
        try {
            advertisementService.advertisementHasBeenClicked(dto);
            return ResponseEntity.noContent().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

