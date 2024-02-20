package com.example.localguidebe.repository;

import com.example.localguidebe.entity.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour,Long> {
    
@Query("SELECT tour FROM Tour tour JOIN tour.categories category WHERE LOWER(tour.name) LIKE %:searchName% AND tour.pricePerTraveler >= :minPrice AND tour.pricePerTraveler <= :maxPrice AND category.id = :categoryId")
Page<Tour> findTours(@Param("searchName") String searchName, @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice, @Param("categoryId") Long categoryId, Pageable pageable);

}
