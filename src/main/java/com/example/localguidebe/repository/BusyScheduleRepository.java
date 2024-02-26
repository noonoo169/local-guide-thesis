package com.example.localguidebe.repository;

import com.example.localguidebe.entity.BusySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusyScheduleRepository extends JpaRepository<BusySchedule,Integer> {
    @Query("SELECT busySchedule FROM BusySchedule busySchedule JOIN busySchedule.guide guide WHERE guide.id = :id")
    List<BusySchedule> findAllByGuideId(@Param("id") Long id);
}
