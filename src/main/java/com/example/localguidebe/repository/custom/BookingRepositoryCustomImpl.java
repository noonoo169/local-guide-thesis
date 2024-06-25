package com.example.localguidebe.repository.custom;

import com.example.localguidebe.dto.RemainingSeatByStartDateTimeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
@Slf4j
public class BookingRepositoryCustomImpl implements BookingRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RemainingSeatByStartDateTimeDTO> findDateTimes(List<LocalDateTime> dateTimes, Long tourId) {
        String sql = buildSqlQuery(dateTimes, tourId);
        Query query = entityManager.createNativeQuery(sql);

        List<Object[]> results = query.getResultList();

        return results.stream()
                .map(row -> new RemainingSeatByStartDateTimeDTO(
                        ((Number) row[0]).intValue(),
                        LocalDateTime.parse(row[1].toString().replace(' ', 'T'))
                ))
                .collect(Collectors.toList());
    }

    private String buildSqlQuery(List<LocalDateTime> dateTimes, Long tourId) {
    return "SELECT \n"
        + "    t.limit_traveler - SUM(b.number_travelers) AS remaining_seats, \n"
        + "    b.start_date\n"
        + "FROM \n"
        + "    Booking b \n"
        + "JOIN \n"
        + "    tour t ON b.tour_id = t.id\n"
        + "JOIN "
        + "("
        + dateTimes.stream()
            .map(dt -> "SELECT '" + dt.toString().replace('T', ' ') + "' AS TimeValue")
            .collect(Collectors.joining(" UNION ALL "))
        + ") AS temp\n"
        + "ON \n"
        + "    b.start_date = temp.TimeValue\n"
        + "WHERE \n"
        + "    t.id = " + tourId +"\n"
        + "GROUP BY \n"
        + "    b.tour_id, b.start_date;";
    }
}
