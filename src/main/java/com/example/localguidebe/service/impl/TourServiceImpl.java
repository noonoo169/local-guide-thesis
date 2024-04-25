package com.example.localguidebe.service.impl;


import com.example.localguidebe.dto.requestdto.TourRequestDTO;
import com.example.localguidebe.dto.requestdto.UpdateTourRequestDTO;
import com.example.localguidebe.dto.responsedto.SearchTourDTO;
import com.example.localguidebe.entity.*;

import com.example.localguidebe.converter.TourToTourDtoConverter;
import com.example.localguidebe.dto.TourDTO;


import com.example.localguidebe.converter.TourToTourDtoConverter;
import com.example.localguidebe.dto.TourDTO;

import com.example.localguidebe.enums.RolesEnum;
import com.example.localguidebe.repository.TourRepository;
import com.example.localguidebe.repository.TourStartTimeRepository;
import com.example.localguidebe.service.CategoryService;
import com.example.localguidebe.service.LocationService;
import com.example.localguidebe.service.TourService;
import com.example.localguidebe.service.TourStartTimeService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {
    private TourToTourDtoConverter tourToTourDtoConverter;
    @Autowired
    public void setTourToDtoConverter( TourToTourDtoConverter tourToTourDtoConverter){
        this.tourToTourDtoConverter = tourToTourDtoConverter;
    }
    private TourRepository tourRepository;
    private final CategoryService categoryService;
    private final TourStartTimeService tourStartTimeService;
    private final LocationService locationService;
    @Autowired
    public TourServiceImpl(CategoryService categoryService,
                           TourStartTimeService tourStartTimeService,
                           LocationService locationService) {
        this.categoryService = categoryService;
        this.tourStartTimeService = tourStartTimeService;
        this.locationService = locationService;
    }

    @Autowired
    public void setTourRepository(TourRepository tourRepository){
        this.tourRepository = tourRepository;
    }

    @Override
    public List<TourDTO> getListTour() {

        List<TourDTO> tourDTOS = new ArrayList<>();

        List<Tour> tours =tourRepository.findAll();
        for(Tour tour : tours){
            tourDTOS.add(  tourToTourDtoConverter.convert(tour));
        }
        return tourDTOS ;
    }

    @Override
    public Tour saveTour(TourRequestDTO tourRequestDTO) {
        Tour newTour = new Tour();
        BeanUtils.copyProperties(tourRequestDTO, newTour, "categories");
        tourRequestDTO.getCategories().stream().forEach(category -> newTour.getCategories().add(categoryService.getCategoryById(category.getId())));
        return tourRepository.save(newTour);

    }

    @Transactional
    @Override

    public Tour updateTour(UpdateTourRequestDTO updateTourRequestDTO) {
        Optional<Tour> optionalTour = tourRepository.findById(updateTourRequestDTO.id());
        if (optionalTour.isPresent()) {
            Tour tour = optionalTour.get();
            BeanUtils.copyProperties(updateTourRequestDTO, tour, "province",
                    "categories",
                    "tourStartTimes",
                    "meetingPoint",
                    "locations");

            // Update tour start time
            if (updateTourRequestDTO.tourStartTimes() != null) {
                tour.getTourStartTimes().forEach(tourStartTime -> tourStartTimeService.deleteById(tourStartTime.getId()));
                tour.getTourStartTimes().clear();
                updateTourRequestDTO.tourStartTimes().forEach(tourStartTime -> {
                    tourStartTime.setTour(tour);
                    tour.getTourStartTimes().add(tourStartTime);
                });
            }

            // Update category
            if (updateTourRequestDTO.category_ids() != null) {
                boolean isUpdateCategory = !tour.getCategories().stream().map(Category::getId).sorted().collect(Collectors.toList())
                        .equals(updateTourRequestDTO.category_ids().stream().sorted().collect(Collectors.toList()));
                if (isUpdateCategory) {
                    tour.getCategories().clear();
                    updateTourRequestDTO.category_ids().forEach(categoryId -> {
                        tour.getCategories().add(categoryService.getCategoryById(categoryId));
                    });
                }
            }

            // TODO Update image

            // Update locations
            if (updateTourRequestDTO.locations() != null) {
                tour.getLocations().clear();
                updateTourRequestDTO.locations().forEach(location -> {
                    if (location.getId() != null) tour.getLocations().add(locationService.findById(location.getId()));
                    else
                        tour.getLocations().add(new Location(location.getName(), location.getLatitude(), location.getLongitude()));
                });
            }

            // Update meetingPoint
            if (updateTourRequestDTO.meetingPoint() != null && updateTourRequestDTO.meetingPoint().getId() == null) {
                Location newMeetingPoint = new Location(updateTourRequestDTO.meetingPoint().getName(), updateTourRequestDTO.meetingPoint().getLatitude(), updateTourRequestDTO.meetingPoint().getLongitude());
                locationService.save(newMeetingPoint);
                tour.setMeetingPoint(newMeetingPoint);
            }

            // Update province
            if (updateTourRequestDTO.province() != null && updateTourRequestDTO.province().getId() == null) {
                Location newProvince = new Location(updateTourRequestDTO.province().getName(), updateTourRequestDTO.province().getLatitude(), updateTourRequestDTO.province().getLongitude());
                locationService.save(newProvince);
                tour.setProvince(newProvince);
            }
            return tourRepository.save(tour);
        }
        return null;
    }
    

    @Override
    public TourDTO getTourById(Long id) {
        return tourToTourDtoConverter.convert(tourRepository.findById(id).orElseThrow());

    }

    @Override
    public SearchTourDTO getTours(Integer page, Integer limit, String sortBy, String order, String searchName,Double minPrice,Double maxPrice,Long categoryId) {
        Sort sort = order.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable paging = PageRequest.of(page, limit, sort);
        Page<Tour> tourPage = tourRepository.findTours(searchName,minPrice,maxPrice,categoryId,paging);
        return new SearchTourDTO (tourPage.get().map(tourToTourDtoConverter::convert).collect(Collectors.toList()), tourPage.getTotalPages());
    }


}
