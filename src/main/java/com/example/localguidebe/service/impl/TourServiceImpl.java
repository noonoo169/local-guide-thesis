package com.example.localguidebe.service.impl;

import com.example.localguidebe.dto.requestdto.UpdateTourRequestDTO;
import com.example.localguidebe.entity.Category;
import com.example.localguidebe.entity.Location;
import com.example.localguidebe.entity.Tour;
import com.example.localguidebe.repository.TourRepository;
import com.example.localguidebe.repository.TourStartTimeRepository;
import com.example.localguidebe.service.CategoryService;
import com.example.localguidebe.service.LocationService;
import com.example.localguidebe.service.TourService;
import com.example.localguidebe.service.TourStartTimeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TourServiceImpl implements TourService {
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
    public List<Tour> getListTour() {
        return tourRepository.findAll();
    }

    @Override
    public Tour saveTour(Tour tour) {
        return tourRepository.save(tour);
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
            if (updateTourRequestDTO.category_ids() !=  null) {
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
                    else tour.getLocations().add(new Location(location.getName(), location.getLatitude(), location.getLongitude()));
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
    public Tour getTourById(Long id) {
        return tourRepository.findById(id).orElseThrow();
    }
}
