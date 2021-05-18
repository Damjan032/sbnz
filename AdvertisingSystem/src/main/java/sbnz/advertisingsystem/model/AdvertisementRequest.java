package sbnz.advertisingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdvertisementRequest {
    private Advertisement advertisement;
    private int minAge;
    private int maxAge;
    private String geographicLocation;
}
