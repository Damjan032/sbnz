package com.sbnz.adsys.controller;

import com.sbnz.adsys.sevice.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private UserService userService;
    
    @GetMapping()
	public ResponseEntity<Void> getUsers() {
        System.out.println("USAO SAM");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
