package com.sbnz.adsys.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    private String title;

    private String content;

    private String targetUrl;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Tag> tags;

    @OneToMany(mappedBy="user")
    private List<Event> events;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="advertiser_id", nullable=false)
    private Advertiser advertiser;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<SocialMediaUser> socialMediaUsersToBeShow;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<SocialMediaUser> socialMediaUsersSeen;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<SocialMediaUser> socialMediaUsersIgnored;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<SocialMediaUser> socialMediaUsersClicked;
    
    private LocalDate datePublished;

    @Override
    public String toString() {
        return title + " by: " + advertiser.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        else if (obj instanceof Advertisement) {
            return this.id.equals(((Advertisement) obj).getId());
        }
        return false;
    }
}
