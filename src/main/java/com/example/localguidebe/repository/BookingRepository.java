package com.example.localguidebe.repository;

import com.example.localguidebe.entity.Booking;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
  /*
  TODO: Consider whether to retrieve paid bookings or all bookings.
        If retrieving all bookings, set a timeout for pending_payment bookings.
   */
  @Query(
      "SELECT FUNCTION('time', b.startDate) FROM Booking b WHERE b.tour.id = :tourId AND FUNCTION('date', b.startDate) = :startDate")
  List<String> findStartDateTimesByTourIdAndStartDate(
      @Param("tourId") Long tourId, @Param("startDate") LocalDate startDate);
}
