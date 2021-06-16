package com.sbnz.adsys.controller;

import com.sbnz.adsys.dto.SocialMediaPageDTO;
import com.sbnz.adsys.dto.TagDTO;
import com.sbnz.adsys.service.SocialMediaPageService;
import com.sbnz.adsys.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/api/pages", produces = MediaType.APPLICATION_JSON_VALUE)
public class SocialMediaPageController {

    @Autowired
    private SocialMediaPageService pageService;

    @GetMapping
    public ResponseEntity<List<SocialMediaPageDTO>> findAll() {
        List<SocialMediaPageDTO> dtos = pageService.findAll();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
