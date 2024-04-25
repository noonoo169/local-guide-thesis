package com.example.localguidebe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "location")
public class Location {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Column(name = "name")
  private String name;


  @Column(name = "latitude")
  private String latitude;


  @Column(name = "longitude")
  private String longitude;

//  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "location")
//  private List<Image> images = new ArrayList<>();

  @ManyToMany(mappedBy = "locations", fetch = FetchType.LAZY)
  private Set<Tour> tours = new HashSet<>();

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "meetingPoint")
  private Set<Tour> toursOfMeetingPoint = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Location that)) {
      return false;
    }

    return getId().equals(that.getId());
  }

  public Location(String name, String latitude, String longitude) {
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
  }
}
