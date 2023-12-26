package com.scaler.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Screen extends BaseModel{

    private String screenNumber;

    @OneToMany
    private List<Seat> seats;
    
    @Enumerated(EnumType.ORDINAL)
    @ElementCollection
    private List<Features> features;
}
