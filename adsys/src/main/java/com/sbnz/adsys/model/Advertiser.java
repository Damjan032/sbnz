package com.sbnz.adsys.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Advertiser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String picture;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="advertiser", orphanRemoval = true)
    private List<Advertisement> advertisements;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof Advertiser) {
            return this.name.equals(((Advertiser) obj).getName());
        }
        return false;
    }
}
