package com.scaler.bookmyshow.repositories;

import com.scaler.bookmyshow.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    @Override
    Optional<ShowSeat> findById(Long aLong);

    @Override
    ShowSeat save(ShowSeat showSeat); // Upsert -> Update + Insert
    // If ShowSeat object is not present in the DB insert it
    // Else Update the ShowSeat object in the DB
}
