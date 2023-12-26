package com.scaler.bookmyshow.dtos;

import com.scaler.bookmyshow.models.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMovieResponseDto {
    private Long bookingId;
    private double amount;
    private ResponseStatus responseStatus;
}
