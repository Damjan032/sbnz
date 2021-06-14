package com.sbnz.adsys.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TagDTO {
    Long id;
    private String value;
}
