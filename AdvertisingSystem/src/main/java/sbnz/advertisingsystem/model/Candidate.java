package sbnz.advertisingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Candidate {
    private SocialMediaUser user;
    private boolean isAgeGroup, isGeographyGroup;
    private boolean isTargetGroup;
    double c1, c2, c3, c4;
    String test = "test";
}
