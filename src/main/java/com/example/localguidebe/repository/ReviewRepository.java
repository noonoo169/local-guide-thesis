package com.example.localguidebe.repository;

import com.example.localguidebe.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer>{
    List<Review> getReviewsByGuideId(Long guideId);
}
