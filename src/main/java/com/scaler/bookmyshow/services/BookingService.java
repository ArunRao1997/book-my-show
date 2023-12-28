package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.exceptions.ShowNotFoundException;
import com.scaler.bookmyshow.exceptions.ShowSeatNotAvailableException;
import com.scaler.bookmyshow.exceptions.UserNotFoundException;
import com.scaler.bookmyshow.models.*;
import com.scaler.bookmyshow.repositories.BookingRepository;
import com.scaler.bookmyshow.repositories.ShowRepository;
import com.scaler.bookmyshow.repositories.ShowSeatRepository;
import com.scaler.bookmyshow.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private BookingRepository bookingRepository;

    private PriceCalculatorService priceCalculatorService;

    public BookingService(UserRepository userRepository,
                          ShowRepository showRepository,
                          ShowSeatRepository showSeatRepository,
                          BookingRepository bookingRepository,
                          PriceCalculatorService priceCalculatorService) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.bookingRepository = bookingRepository;
        this.priceCalculatorService = priceCalculatorService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovie(Long userId, Long showId, List<Long> showSeatIds) {
        /*
        -----------TAKE A LOCK----------------
        1. Get user from userId
        2. Get show from showId
        3. Get the list of showSeats from showSeatIds
        4. Check if all the showSeats are available
        5. If any of the selected seats are not available throw an exception
        6. If all the selected seats are available, then changed the status to be locked
        7. Change the status in DB as well
        8. Create the Booking object, and store it in DB
        9. Return the Booking object
        ------------RELEASE THE LOCK---------------
         */
        // 1. Get user from userId
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("Invalid UserId ");
        }
        User bookedBy = optionalUser.get();
        ;

        // 2. Get show from showId
        Optional<Show> optionalShow = showRepository.findById(showId);
        if (optionalShow.isEmpty()) {
            throw new ShowNotFoundException("Invalid ShowId");
        }
        Show show = optionalShow.get();

        // 3. Get the list of showSeats from showSeatIds
        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);

        // 4. Check if all the showSeats are available
        for (ShowSeat showSeat : showSeats) {
            if (!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)) {
                // 5. If any of the selected seats are not available throw an exception
                throw new ShowSeatNotAvailableException("ShowSeat with id: " + showSeat.getId() + " is not available");
            }
        }

        List<ShowSeat> bookedShowSeats = new ArrayList<>();
        // 6. If all the selected seats are available, then changed the status to be blocked
        for (ShowSeat showSeat : showSeats) {
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
            // 7. Change the status in DB as well
            bookedShowSeats.add(showSeatRepository.save(showSeat));
        }
        // 8. Create the Booking object, and store it in DB
        Booking booking = new Booking();
        booking.setUser(bookedBy);
        booking.setBookingStatus(BookingStatus.IN_PROGRESS);
        booking.setPayments(new ArrayList<>());
        booking.setShowSeats(bookedShowSeats);
        booking.setCreatedAt(new Date());
        booking.setLastModifiedAt(new Date());
        booking.setAmount(priceCalculatorService.calculateBookingPrice(bookedShowSeats, show));

        return bookingRepository.save(booking);
        // ------LOCK WILL BE RELEASED------
    }
}