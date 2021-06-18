package com.sbnz.adsys.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AdvertisementEventDTO {
    @NotNull
    private long userId;

    @NotNull
    private long advertisementId;
}
