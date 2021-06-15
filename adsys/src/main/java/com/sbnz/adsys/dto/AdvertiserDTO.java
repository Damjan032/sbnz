package com.sbnz.adsys.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AdvertiserDTO {
    private Long id;

    @NotBlank
    private String name;

    private String picture;
}
