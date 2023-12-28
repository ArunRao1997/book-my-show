package com.scaler.bookmyshow.services;

import com.scaler.bookmyshow.models.Show;
import com.scaler.bookmyshow.models.ShowSeat;
import com.scaler.bookmyshow.models.ShowSeatType;
import com.scaler.bookmyshow.repositories.ShowSeatRepository;
import com.scaler.bookmyshow.repositories.ShowSeatTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceCalculatorService {
    private ShowSeatTypeRepository showSeatTypeRepository;

    public PriceCalculatorService(ShowSeatTypeRepository showSeatTypeRepository) {
        this.showSeatTypeRepository = showSeatTypeRepository;
    }

    public int calculateBookingPrice(List<ShowSeat> showSeats, Show show){
        int amount = 0;
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show.getId());
        for(ShowSeat showSeat: showSeats){
            for(ShowSeatType showSeatType: showSeatTypes){
                if(showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType())){
                    amount += showSeatType.getPrice();
                }
            }
            // Inner for loop can be removed if we use HashMap of ShowSeatType and Price
        }
        return amount;
    }
}
