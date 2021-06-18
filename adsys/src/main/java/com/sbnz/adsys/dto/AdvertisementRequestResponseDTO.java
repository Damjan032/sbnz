package com.sbnz.adsys.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AdvertisementRequestResponseDTO {
    private Long id;

    @NotNull
    private AdvertisementDTO advertisement;

    @NotNull
    private List<CandidateDTO> candidates;

    private LocalDateTime date;
}