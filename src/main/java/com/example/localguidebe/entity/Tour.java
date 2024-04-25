package com.example.localguidebe.entity;

import com.example.localguidebe.enums.AssociateName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@Builder
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


  @Column(name = "transportation", columnDefinition = "TEXT")
  private String transportation;


  @Column(name = "include_service", columnDefinition = "TEXT")
  private String includeService;


  @Column(name = "duration")
  private Integer duration;


  @Column(name = "unit")
  private String unit;

  @Column(name = "estimated_local_cash_needed", columnDefinition = "TEXT")
  private String estimatedLocalCashNeeded;


  @Column(name = "price_per_traveler")
  private Double pricePerTraveler;


  @Column(name = "limit_traveler")
  private Integer limitTraveler;


  @Column(name = "extra_price")
  private Double extraPrice;


  @Column(name = "overall_rating",columnDefinition = "double default 0.0")
  private Double overallRating = 0.0;


  @Column(name = "itinerary", columnDefinition = "TEXT")
  private String itinerary;

  @Column(name = "is_deleted", columnDefinition = "boolean default false")
  private Boolean isDeleted = Boolean.FALSE;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "guide_id")
  private User guide;

  @Column private String address;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tour")
  private List<TourStartTime> tourStartTimes = new ArrayList<>();

  @JsonIgnoreProperties(
      allowSetters = true,
      value = {"tours"})
  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(
      name = "category_tour",
      joinColumns = @JoinColumn(name = "tour_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
  private Set<Category> categories = new HashSet<>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tour")
  private List<Review> reviews = new ArrayList<>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tour")
  private List<Booking> bookings = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "meeting_point_id")
  private Location meetingPoint;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(
      name = "location_tour",
      joinColumns = @JoinColumn(name = "tour_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "location_id", referencedColumnName = "id"))
  private Set<Location> locations = new HashSet<>();

  @Column(name = "is_for_specific_traveler", columnDefinition = "boolean default false")
  private Boolean isForSpecificTraveler = Boolean.FALSE;
}
