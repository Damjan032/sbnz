package com.sbnz.adsys.dto;

import com.sbnz.adsys.model.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthTokenDTO {
    private BasicUserInfoDTO user;
    private String accessToken;
    private List<Authority> authorities;
}
