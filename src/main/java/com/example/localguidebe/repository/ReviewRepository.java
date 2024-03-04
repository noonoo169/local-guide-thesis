package com.example.localguidebe.repository;

import com.example.localguidebe.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
  List<Review> getReviewsByGuideId(Long guideId);

  @Modifying
  @Query("delete from Review r where r.id = :id")
  void deleteById(@Param("id") Long id);
}
