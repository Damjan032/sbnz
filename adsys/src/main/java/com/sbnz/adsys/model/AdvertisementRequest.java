package com.sbnz.adsys.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class AdvertisementRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @NotNull
    private int minAge = 0;
    
    @NotNull
    private int maxAge = 999;

    @NotNull
    private double budget;
    
    @NotNull
    private String geographicLocation = "";

    @OneToOne
    private Advertisement advertisement;
}
