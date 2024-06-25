package com.example.localguidebe.repository.custom;

import com.example.localguidebe.dto.RemainingSeatByStartDateTimeDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepositoryCustom {
    List<RemainingSeatByStartDateTimeDTO> findDateTimes(List<LocalDateTime> dateTimes, Long tourId);
}
