package com.example.localguidebe.entity;

import com.example.localguidebe.enums.RolesEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tour")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name ="transportation", columnDefinition = "TEXT")
    private String transportation;
    @Column(name = "include_service",columnDefinition = "TEXT")
    private String includeService;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "unit")
    private String unit;
    @Column(name = "estimated_local_cash_needed",columnDefinition = "TEXT")
    private  String estimatedLocalCashNeeded;
    @Column(name = "price_per_traveler")
    private Double pricePerTraveler;
    @Column(name = "limit_traveler")
    private Integer limitTraveler;
    @Column(name = "extra_price")
    private Double extraPrice;
    @Column(name = "province")
    private String province;
    @Column(name = "overall_rating")
    private Double overallRating;
    @Column(name = "itinerary" ,columnDefinition = "TEXT")
    private String itinerary;
    @ManyToOne
    @JoinColumn(name = "guide_id")
    private User guide;
    //TODO meeting_point_id associate to location

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tour")
    private List<TourStartTime> tourStartTimes = new ArrayList<>();
     @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "category_tour",
            joinColumns = @JoinColumn(name = "tour_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<Category> categories = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tour")
    private List<Review> reviews = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tour")
    private List<Booking> bookings = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tour")
    private List<Image> images = new ArrayList<>();
    @ManyToMany(mappedBy = "tours")
    private Set<Location> locations;
    @OneToOne
    @JoinColumn(name = "meeting_point_id")
    private Location location;


}
