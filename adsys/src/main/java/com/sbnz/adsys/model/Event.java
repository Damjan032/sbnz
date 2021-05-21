package com.sbnz.adsys.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Event {
    protected Date occurredAt;
    protected User user;
    protected Advertisement advertisement;
}
