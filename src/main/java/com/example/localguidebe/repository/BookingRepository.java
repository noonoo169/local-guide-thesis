package com.example.localguidebe.repository;

import com.example.localguidebe.dto.ProvinceResponseDTO;
import com.example.localguidebe.entity.Booking;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

  @Modifying
  @Query("update Booking b set b.status = 'PAID' where b.id = :bookingId")
  void setBookingStatusToPaid(@Param("bookingId") Long bookingId);

  @Modifying
  @Query("update Booking b set b.isDeleted = true where b.id = :id")
  void deleteBookingById(@Param("id") Long id);

  @Query("SELECT b FROM Booking b JOIN b.tour t JOIN t.guide g WHERE g.email = :email")
  List<Booking> getAllBookingByGuider(@Param("email") String email);

  @Query("SELECT b FROM Booking b JOIN b.cart cart WHERE  cart.traveler.id = :travelerId AND b.status ='PAID' ")
  List<Booking> getBookingHistory(@Param("travelerId") Long travelerId);
  @Query("SELECT SUBSTRING_INDEX(t.address, ', ', -1) AS cityProvince, COUNT(b) AS totalBookings " +
          "FROM Tour t " +
          "JOIN t.bookings b " +
          "JOIN t.locations lt " +
          "GROUP BY cityProvince " +
          "ORDER BY totalBookings DESC ")
  List<Object[]> FindForSuggestedTours();


}
