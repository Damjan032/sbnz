package com.sbnz.adsys.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class AdvertisementRequestResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Advertisement advertisement;

    @OneToMany
    private List<Candidate> candidates;

    private LocalDateTime date;
}
