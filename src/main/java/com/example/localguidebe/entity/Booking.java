package com.example.localguidebe.entity;

import com.example.localguidebe.dto.ProvinceResponseDTO;
import com.example.localguidebe.enums.BookingStatusEnum;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking")
@NamedNativeQuery(
    name = "findTotalBookingsByCityProvince",
    query =
        "select \n"
            + "    substring_index(l.address, ', ', -2) name,\n"
            + "    count(*) bookedQuantity,\n"
            + "    i.image_link imageLink\n"
            + "from\n"
            + "    booking b\n"
            + "    join tour t on b.tour_id = t.id\n"
            + "    join location_tour lt on t.id = lt.tour_id\n"
            + "    join location l on lt.location_id = l.id\n"
            + "    join image i on l.address like CONCAT('%', i.province_name, '%')\n"
            + "where b.status = 'PAID'\n"
            + "group by name, imageLink\n"
            + "order by bookedQuantity desc",
    resultSetMapping = "province_popular_dto")
@SqlResultSetMapping(
    name = "province_popular_dto",
    classes =
        @ConstructorResult(
            targetClass = ProvinceResponseDTO.class,
            columns = {
              @ColumnResult(name = "name", type = String.class),
              @ColumnResult(name = "bookedQuantity", type = Long.class),
              @ColumnResult(name = "imageLink", type = String.class)
            }))
public class Booking {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "start_date")
  private LocalDateTime startDate;

  @Range(min = 0, max = 50)
  @Column(name = "number_travelers")
  private Integer numberTravelers;

  @Range(min = 0, max = 100000000)
  @Column(name = "price")
  private Double price;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private BookingStatusEnum status;

  @Column(columnDefinition = "boolean default false")
  private boolean isDeleted;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "cart_id")
  private Cart cart;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "tour_id")
  private Tour tour;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "invoice_id")
  private Invoice invoice;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "tour_dupe_id")
  private TourDupe tourDupe;
}
