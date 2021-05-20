package sbnz.advertisingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class SocialMediaUser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @NotNull
    private int age;
    
    @NotNull
    private User user;
    
    @NotNull
    private String country;
    
    @NotNull
    private String city;
    
    
    @ManyToMany(mappedBy = "usersWhoLikeThePage")
    private List<SocialMediaPage> likedSocialMediaPages;

    @ManyToMany
    private List<Advertisement> advertisementsToBeShown;

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
}
