package com.sbnz.adsys.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class SocialMediaUser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    private int age;
    
    @OneToOne
    private User user;
    
    private String country;
    
    private String city;
    
    @ManyToMany(mappedBy = "usersWhoLikeThePage")
    private List<SocialMediaPage> likedSocialMediaPages;

    @ManyToMany
    private List<Advertisement> advertisementsToBeShown;

    @ManyToMany
    private List<Advertisement> seenAdvertisements;

    @ManyToMany
    private List<Advertisement> ignoredAdvertisements;

    @ManyToMany
    private List<Advertisement> clickedAdvertisements;

    public String fullName() {
        return user.getFirstName() + " " + user.getLastName();
    }

    public String fullLocation() {
        return city + ", " + country;
    }

    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public String toString() {
        return fullName();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        else if (obj instanceof SocialMediaUser){
            SocialMediaUser user = (SocialMediaUser) obj;
            return this.user.equals(user.getUser());
        }
        return false;
    }
}
