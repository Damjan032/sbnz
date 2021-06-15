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
public class SocialMediaPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;

    private String picture;

    @ManyToMany
    private List<Tag> categoryKeywords;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private List<SocialMediaUser> usersWhoLikeThePage;
}
