package com.sbnz.adsys.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SocialMediaPageDTO {
    private Long id;

    @NotBlank
    private String name;

    private List<String> categoryKeywords;
}
