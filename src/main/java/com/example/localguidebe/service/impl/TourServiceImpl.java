package com.example.localguidebe.service.impl;

import com.example.localguidebe.entity.Tour;
import com.example.localguidebe.repository.TourRepository;
import com.example.localguidebe.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourServiceImpl implements TourService {
    private TourRepository tourRepository;
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

    @Override
    public Tour getTourById(Long id) {
        return tourRepository.findById(id).orElseThrow();
    }


}
