package com.sbnz.adsys.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BasicUserInfoDTO {

    private long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int age;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String city;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String country;
}
