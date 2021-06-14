package com.sbnz.adsys.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AdvertisementRequestDTO {
    private long id;

    @NotNull
    private int minAge;

    @NotNull
    private int maxAge;

    @NotBlank
    private String geographicLocation;

    @NotNull
    private double budget;

    @NotNull
    private AdvertisementDTO advertisement;
}
