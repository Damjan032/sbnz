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
public class SocialMediaPage {

    private String name;
    private List<String> categoryKeywords;
    private List<SocialMediaUser> usersWhoLikeThePage;
}
