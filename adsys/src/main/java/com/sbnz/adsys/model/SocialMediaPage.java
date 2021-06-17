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
public class SocialMediaPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String picture;

    @ManyToMany
    private List<Tag> categoryKeywords;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SocialMediaUser> usersWhoLikeThePage;

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        else if (obj instanceof SocialMediaPage){
            SocialMediaPage page = (SocialMediaPage) obj;
            return this.getName().equals(page.getName());
        }
        return false;
    }
}
