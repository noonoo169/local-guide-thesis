package com.example.localguidebe.repository;

import com.example.localguidebe.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
  @Query("SELECT c FROM Cart c JOIN FETCH c.bookings b WHERE c.traveler.email = :email AND b.status = 'PENDING_PAYMENT'")
  Optional<Cart> getCartByEmail(@Param("email") String email);
  @Query("Select c FROM Cart c WHERE c.traveler.email = :email")
  Optional<Cart> getCartByTravelerEmail(@Param("email") String email);
}
