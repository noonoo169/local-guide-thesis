package com.example.localguidebe.repository;

import com.example.localguidebe.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
  List<Review> getReviewsByGuideId(Long guideId);
}
