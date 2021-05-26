package com.sbnz.adsys.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Candidate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @OneToOne
    private SocialMediaUser user;
    private boolean isAgeGroup, isGeographyGroup;
    private boolean isTargetGroup;
    double c1, c2, c3, c4;
    String test = "test";
}
