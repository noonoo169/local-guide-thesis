package com.example.localguidebe.entity;

import com.example.localguidebe.enums.BookingStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE booking SET is_deleted=true WHERE id = ?")
@Table(name = "booking")
public class Booking {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Column(name = "start_date")
  private LocalDateTime startDate;


  @Range(min=0, max=50)
  @Column(name = "number_travelers")
  private Integer numberTravelers;


  @Range(min=0, max=100000000)
  @Column(name = "price")
  private Double price;


  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private BookingStatusEnum status;

  @Column(columnDefinition = "boolean default false")
  private boolean isDeleted;

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
