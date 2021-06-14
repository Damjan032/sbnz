package com.sbnz.adsys.dto;

import com.sbnz.adsys.model.Advertisement;
import com.sbnz.adsys.model.SocialMediaPage;
import com.sbnz.adsys.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SocialMediaUserDTO {
    private Long id;

    @NotNull
    private int age;

    @OneToOne
    private User user;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    private List<SocialMediaPageDTO> likedSocialMediaPages;

    private List<AdvertisementDTO> advertisementsToBeShown;

    private List<AdvertisementDTO> seenAdvertisements;

    private List<AdvertisementDTO> ignoredAdvertisements;

    private List<AdvertisementDTO> clickedAdvertisements;
}
