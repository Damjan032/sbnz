package com.sbnz.adsys.dto;

import com.sbnz.adsys.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdvertisementDTO {
    private Long id;
    private String title;
    private String content;
    private String targetUrl;
    private List<String> tags;
    private double budget;
    private AdvertiserDTO advertiser;
}
