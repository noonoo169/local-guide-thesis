package com.example.localguidebe.entity;

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
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "location")
    private List<Image> images = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "tour_location",
            joinColumns = @JoinColumn(name = "location_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tour_id", referencedColumnName = "id"))
    private Set<Tour> tours = new HashSet<>();
    @OneToMany(mappedBy = "province")
    private Set<Tour> tours1 = new HashSet<>();

}
