package com.example.localguidebe.entity;

import com.example.localguidebe.enums.BookingStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(name = "start_date")
  private LocalDateTime startDate;

  @NotNull
  @Size(min = 0, max = 50)
  @Column(name = "number_travelers")
  private Integer numberTravelers;

  @NotNull
  @Size(min = 0, max = 1000000)
  @Column(name = "price")
  private Double price;

  @NotNull
  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private BookingStatusEnum status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_id")
  private Cart cart;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tour_id")
  private Tour tour;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "invoice_id")
  private Invoice invoice;
}
