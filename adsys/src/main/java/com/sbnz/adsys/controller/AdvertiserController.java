package com.sbnz.adsys.controller;

import com.sbnz.adsys.dto.AdvertiserDTO;
import com.sbnz.adsys.service.AdvertiserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/advertisers", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdvertiserController {

    @Autowired
    private AdvertiserService advertiserService;

    @GetMapping
    public ResponseEntity<List<AdvertiserDTO>> findAll() {
        List<AdvertiserDTO> advertiserDTOS = advertiserService.findAll();
        return new ResponseEntity<>(advertiserDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AdvertiserDTO> create(@RequestBody AdvertiserDTO advertiserDTO) {
        AdvertiserDTO createdDTO = advertiserService.create(advertiserDTO);
        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }

}
