package com.example.localguidebe.dto;

import com.example.localguidebe.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TourDTO {

    private Long id;

    private String name;

    private String description;

    private String transportation;

    private String includeService;

    private Integer duration;

    private String unit;

    private  String estimatedLocalCashNeeded;

    private Double pricePerTraveler;

    private Integer limitTraveler;

    private Double extraPrice;

    private Double overallRating;

    private String itinerary;

    private UserDTO guide;
//    //TODO meeting_point_id associate to location
//
//    private Location province;


//    private List<TourStartTime> tourStartTimes = new ArrayList<>();

    private Set<CategoryDTO> categories = new HashSet<>();

//    private List<Review> reviews = new ArrayList<>();
//
//    private List<Booking> bookings = new ArrayList<>();
//
    private List<ImageDTO> images = new ArrayList<>();
//
//    private Set<Location> locations;
//
//    private Location location;
}
