package com.example.localguidebe.repository;

import com.example.localguidebe.entity.BusySchedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BusyScheduleRepository extends JpaRepository<BusySchedule, Integer> {
  @Query(
      "SELECT busySchedule FROM BusySchedule busySchedule JOIN busySchedule.guide guide WHERE guide.id = :id")
  List<BusySchedule> findAllByGuideId(@Param("id") Long id);

  @Query(
      "SELECT busySchedule FROM BusySchedule busySchedule WHERE busySchedule.TypeBusyDay = 'DATE_SELECTED_BY_GUIDE'")
  List<BusySchedule> getBusySchedules();
}
