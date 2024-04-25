package com.example.localguidebe.controller;

import com.example.localguidebe.converter.TourToTourDtoConverter;
import com.example.localguidebe.dto.requestdto.TourRequestDTO;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tour-management")
@CrossOrigin("*")
public class TourController {
    private TourService  tourService;
    private CategoryService categoryService;
    private TourToTourDtoConverter tourToTourDtoConverter;
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
            Tour newTour = new Tour();
            BeanUtils.copyProperties(tourRequestDTO, newTour, "categories");
            tourRequestDTO.getCategories().stream().forEach(category -> newTour.getCategories().add(categoryService.getCategoryById(category.getId())));
            return new ResponseEntity<>(new Result(true, HttpStatus.OK.value(), "account added successfully",  tourToTourDtoConverter.convert(tourService.saveTour(newTour))), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new Result(false, HttpStatus.CONFLICT.value(), "Adding account failed", null), HttpStatus.CONFLICT);
        }

    }
    @GetMapping("/tours")
    public ResponseEntity<Result> getListTour(){
        try {
            List<TourResponseDTO> tourResponseDTOS = new ArrayList<>();
            List<Tour> tours =tourService.getListTour();
            for(Tour tour : tours){
                tourResponseDTOS.add(  tourToTourDtoConverter.convert(tour));
            }
            return new ResponseEntity<>(new Result(true, HttpStatus.OK.value(), "Get the list successfully", tourResponseDTOS), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new Result(false, HttpStatus.CONFLICT.value(), "get the failure list", null), HttpStatus.CONFLICT);
        }
    }
   

}
