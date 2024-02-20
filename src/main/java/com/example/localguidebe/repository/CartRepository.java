package com.example.localguidebe.repository;

import com.example.localguidebe.entity.Cart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
  @Query("Select c FROM Cart c WHERE c.traveler.email = :email")
  Optional<Cart> getCartByEmail(@Param("email") String email);
}
