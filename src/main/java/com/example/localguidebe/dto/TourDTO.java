package com.example.localguidebe.dto;

import com.example.localguidebe.entity.Category;

import java.util.HashSet;
import java.util.Set;

public class TourDTO {
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
    private String province;
    private String itinerary;
    private Set<CategoryDTO> categories = new HashSet<>();
}
