package com.sbnz.adsys.controller;

import com.sbnz.adsys.dto.TagDTO;
import com.sbnz.adsys.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagDTO>> findAll() {
        List<TagDTO> dtos = tagService.findAll();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
