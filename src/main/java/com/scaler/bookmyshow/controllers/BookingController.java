package com.scaler.bookmyshow.controllers;

import com.scaler.bookmyshow.dtos.BookMovieRequestDto;
import com.scaler.bookmyshow.dtos.BookMovieResponseDto;
import com.scaler.bookmyshow.models.Booking;
import com.scaler.bookmyshow.models.ResponseStatus;
import com.scaler.bookmyshow.services.BookingService;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {

    public BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookMovieResponseDto bookMovie(BookMovieRequestDto bookMovieRequestDto) {
        BookMovieResponseDto response = new BookMovieResponseDto();
        try {
           Booking booking = bookingService.bookMovie(bookMovieRequestDto.getUserId(),
                    bookMovieRequestDto.getShowId(),
                    bookMovieRequestDto.getShowSeatIds());
           response.setBookingId(booking.getId());
           response.setResponseStatus(ResponseStatus.CONFIRMED);
           response.setAmount(booking.getAmount() );

        } catch (RuntimeException runtimeException) {
            response.setResponseStatus(ResponseStatus.FAILED);
        }
        return response;
    }

    public Booking cancelMovie() {
        return null;
    }
}
