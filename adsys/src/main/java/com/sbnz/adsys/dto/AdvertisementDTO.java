package com.sbnz.adsys.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AdvertisementDTO {
    private Long id;

    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String targetUrl;

    private List<String> tags;

    @NotNull
    private AdvertiserDTO advertiser;
}
