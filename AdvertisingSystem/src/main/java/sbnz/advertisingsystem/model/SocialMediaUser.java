package sbnz.advertisingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SocialMediaUser extends User {

    private int age;
    private String country;
    private String city;
    private List<SocialMediaPage> likedSocialMediaPages;

    private List<ClickEvent> clickEvents;

    private List<Advertisement> advertisementsToBeShown;
    private List<Advertisement> ignoredAdvertisements;
    private List<Advertisement> clickedAdvertisements;
}
