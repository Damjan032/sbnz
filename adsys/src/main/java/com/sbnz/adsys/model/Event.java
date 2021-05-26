package com.sbnz.adsys.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public abstract class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    protected Date occurredAt;
    
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    protected User user;
    
    @ManyToOne
    @JoinColumn(name="advertisement_id", nullable=false)
    protected Advertisement advertisement;
}
