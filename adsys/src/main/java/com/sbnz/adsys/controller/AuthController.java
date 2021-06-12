package com.sbnz.adsys.controller;

import com.sbnz.adsys.dto.AuthTokenDto;
import com.sbnz.adsys.dto.LoginDTO;
import com.sbnz.adsys.sevice.AuthService;
import com.sbnz.adsys.sevice.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final UserService userDetailsService;

    private final AuthService authService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthTokenDto> login(@Valid @RequestBody LoginDTO loginDto)
            throws AuthenticationException {
        return ResponseEntity.ok(this.authService.login(loginDto));
    }
    

}
