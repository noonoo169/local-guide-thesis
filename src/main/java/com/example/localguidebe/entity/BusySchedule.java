package com.example.localguidebe.entity;

import com.example.localguidebe.enums.TypeBusyDayEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "busy_schedule")
public class BusySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "busy_date")
    private LocalDateTime busyDate;

  @Column
  @Enumerated(EnumType.STRING)
  private TypeBusyDayEnum TypeBusyDay;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id")
    private User guide;

}
