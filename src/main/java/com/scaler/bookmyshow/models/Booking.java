package com.scaler.bookmyshow.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Booking extends BaseModel{

    @ManyToMany
    private List<ShowSeat> showSeats;

    @ManyToOne
    private User user;

    private int amount;

    @OneToMany
    private List<Payment> payments;

    @Enumerated(EnumType.ORDINAL )
    private BookingStatus bookingStatus;
}
