package com.sbnz.adsys.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
    
    private int minAge = 0;
    
    private int maxAge = 999;

    private double budget;
    
    private String geographicLocation = "";

    @OneToOne
    private Advertisement advertisement;

    private LocalDateTime date;
}
