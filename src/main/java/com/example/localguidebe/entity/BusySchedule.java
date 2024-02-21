package com.example.localguidebe.entity;

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
    @NotNull
    @Column(name = "busy_date")
    private LocalDateTime busyDate;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id")
    private User guide;

}
