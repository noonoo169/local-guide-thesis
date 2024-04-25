package com.example.localguidebe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "price_total")
    private Double priceTotal;
    @Column(name = "create_at")
    private Timestamp createAt;
    @ManyToOne
    @JoinColumn(name = "traveler_id")
    private User traveler;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "invoice")
    private List<Booking> bookings = new ArrayList<>();


}
