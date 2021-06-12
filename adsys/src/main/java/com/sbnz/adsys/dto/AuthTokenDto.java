package com.sbnz.adsys.dto;

import com.sbnz.adsys.model.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthTokenDto {
    private Long userId;
    private String username;
    private String accessToken;
    private List<Authority> authorities;
}
