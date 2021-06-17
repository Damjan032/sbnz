package com.sbnz.adsys.controller;

import com.sbnz.adsys.dto.AuthTokenDTO;
import com.sbnz.adsys.dto.LoginDTO;
import com.sbnz.adsys.service.AuthService;
import com.sbnz.adsys.service.UserService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final UserService userDetailsService;

    private final AuthService authService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthTokenDTO> login(@Valid @RequestBody LoginDTO loginDto)
            throws AuthenticationException {
        return ResponseEntity.ok(this.authService.login(loginDto));
    }
    

}
