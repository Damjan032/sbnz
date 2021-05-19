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

    private List<ClickEvent> clickEvents;

    private List<Advertisement> advertisementsToBeShown;
    private List<Advertisement> ignoredAdvertisements;
    private List<Advertisement> clickedAdvertisements;
}
