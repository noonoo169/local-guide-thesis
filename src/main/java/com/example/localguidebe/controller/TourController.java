package com.example.localguidebe.controller;

import com.example.localguidebe.converter.TourToTourDtoConverter;

import com.example.localguidebe.converter.TourToUpdateTourResponseDtoConverter;

import com.example.localguidebe.dto.TourDTO;


import com.example.localguidebe.dto.TourDTO;

import com.example.localguidebe.dto.requestdto.TourRequestDTO;
import com.example.localguidebe.dto.requestdto.UpdateTourRequestDTO;
import com.example.localguidebe.dto.responsedto.TourResponseDTO;
import com.example.localguidebe.entity.Tour;
import com.example.localguidebe.service.CategoryService;
import com.example.localguidebe.service.TourService;
import com.example.localguidebe.system.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tour-management")
@CrossOrigin("*")
public class TourController {
    private TourService  tourService;
    private CategoryService categoryService;
    private TourToTourDtoConverter tourToTourDtoConverter;
    private TourToUpdateTourResponseDtoConverter tourToUpdateTourResponseDtoConverter;

    public TourController(TourToUpdateTourResponseDtoConverter tourToUpdateTourResponseDtoConverter) {
        this.tourToUpdateTourResponseDtoConverter = tourToUpdateTourResponseDtoConverter;
    }

    @Autowired
    public void setTourToDtoConverter( TourToTourDtoConverter tourToTourDtoConverter){
        this.tourToTourDtoConverter = tourToTourDtoConverter;
    }

    @Autowired
    public void setTourService(TourService tourService){
        this.tourService = tourService;
    }
    @Autowired
    public void setCategoryService(CategoryService categoryService){this.categoryService =categoryService;}
    @PostMapping("/add")
    public ResponseEntity<Result> addTour(@RequestBody TourRequestDTO tourRequestDTO){
        try {
            return new ResponseEntity<>(new Result(true, HttpStatus.OK.value(), "account added successfully",  tourToTourDtoConverter.convert(tourService.saveTour(tourRequestDTO))), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new Result(false, HttpStatus.CONFLICT.value(), "Adding account failed", null), HttpStatus.CONFLICT);
        }

    }

    @GetMapping("/tour-detail/{id}")
    public ResponseEntity<Result> getTour(@PathVariable("id") Long id ){
        try {
            return new ResponseEntity<>(new Result(true, HttpStatus.OK.value(), "Get the tour successfully", tourService.getTourById(id)), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new Result(false, HttpStatus.CONFLICT.value(), "No tour found", null), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Result> update(@RequestBody UpdateTourRequestDTO updateTourRequestDTO){
        try {
            Tour tour = tourService.updateTour(updateTourRequestDTO);
            if (tour == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        new Result(
                                false,
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Update tour information failed")
                );
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new Result(
                            true,
                            HttpStatus.OK.value(),
                            "Update tour information successfully",
                            tourToUpdateTourResponseDtoConverter.convert(tour))
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new Result(
                            false,
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage())
            );
        }
    }
    @GetMapping("/tours")
    public ResponseEntity<Result> getListTour(){
        try {
            return new ResponseEntity<>(new Result(true, HttpStatus.OK.value(), "Get the list successfully", tourService.getListTour()), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new Result(false, HttpStatus.CONFLICT.value(), "get the failure list", null), HttpStatus.CONFLICT);
        }
    }
    
}
