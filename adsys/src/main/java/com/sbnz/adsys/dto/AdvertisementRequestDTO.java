package com.sbnz.adsys.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdvertisementRequestDTO {
    private long id;
    private int minAge;
    private int maxAge;
    private String geographicLocation;
    private AdvertisementDTO advertisement;
}
