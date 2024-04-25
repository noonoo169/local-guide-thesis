package com.example.localguidebe.dto.requestdto;

import com.example.localguidebe.dto.LocationDTO;
import com.example.localguidebe.entity.*;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class TourRequestDTO {
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
    private String address;
    private String itinerary;
    private User guide;
    private List<TourStartTime> tourStartTimes = new ArrayList<>();
    private Set<Category> categories = new HashSet<>();
    private List<Review> reviews = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    private Set<LocationDTO> locations;
    private Location location;
}
