package com.example.localguidebe.entity;

import com.example.localguidebe.enums.BookingStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="start_date")
    private Timestamp startDate;
    @Column(name = "number_traveler")
    private Integer numberTraveler;
    @Column(name = "price")
    private Double price;
    @Column(name ="status")
    @Enumerated(EnumType.STRING)
    private BookingStatusEnum status;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;



}
