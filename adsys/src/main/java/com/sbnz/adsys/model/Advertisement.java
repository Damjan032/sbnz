package com.sbnz.adsys.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String targetUrl;
    
    @ManyToMany
    private List<Tag> tags;

    @NotNull
    private double budget;
    
    @OneToMany(mappedBy="user")
    private List<Event> events;
    
    @ManyToOne
    @JoinColumn(name="advertiser_id", nullable=false)
    private Advertiser advertiser;

    @NotNull
    private LocalDate datePublished;
}