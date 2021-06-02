package com.sbnz.adsys.controller;

import com.sbnz.adsys.dto.LoginDTO;
import com.sbnz.adsys.sevice.AuthService;
import com.sbnz.adsys.sevice.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController {
    private AuthService authService;
    
    @GetMapping()
	public ResponseEntity<Void> getUsers() {
        System.out.println("USAO SAM");
        authService.login(new LoginDTO("superadmin@mail.com", "admi5n123"));
       /* authService.login(new LoginDTO("superadmin@mail.com", "admi5n123"));
        authService.login(new LoginDTO("superadmin@mail.com", "admi5n123"));
        authService.login(new LoginDTO("superadmin@mail.com", "admi5n123"));
        authService.login(new LoginDTO("superadmin@mail.com", "admi5n123"));*/
		return new ResponseEntity<>(HttpStatus.OK);
	}
}