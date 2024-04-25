package com.example.localguidebe.service.impl;

import com.example.localguidebe.converter.ToResultInSearchSuggestionDtoConverter;
import com.example.localguidebe.converter.TourToTourDtoConverter;
import com.example.localguidebe.dto.TourDTO;
import com.example.localguidebe.dto.requestdto.TourRequestDTO;
import com.example.localguidebe.dto.requestdto.UpdateTourRequestDTO;
import com.example.localguidebe.dto.responsedto.ResultInSearchSuggestionDTO;
import com.example.localguidebe.dto.responsedto.SearchSuggestionResponseDTO;
import com.example.localguidebe.dto.responsedto.SearchTourDTO;
import com.example.localguidebe.entity.*;
import com.example.localguidebe.enums.AssociateName;
import com.example.localguidebe.enums.FolderName;
import com.example.localguidebe.repository.ImageRepository;
import com.example.localguidebe.repository.TourRepository;


import com.example.localguidebe.service.CategoryService;
import com.example.localguidebe.service.LocationService;
import com.example.localguidebe.service.TourService;
import com.example.localguidebe.service.TourStartTimeService;
import com.example.localguidebe.utils.AddressUtils;
import java.util.Comparator;

import com.example.localguidebe.service.*;

import java.io.IOException;


import com.example.localguidebe.service.*;

import java.io.IOException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.localguidebe.utils.CloudinaryUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TourServiceImpl implements TourService {
  private TourToTourDtoConverter tourToTourDtoConverter;
  private final ToResultInSearchSuggestionDtoConverter toResultInSearchSuggestionDtoConverter;

  @Autowired
  public void setTourToDtoConverter(TourToTourDtoConverter tourToTourDtoConverter) {
    this.tourToTourDtoConverter = tourToTourDtoConverter;
  }
  private final ImageRepository imageRepository;

  private TourRepository tourRepository;
  private final CategoryService categoryService;
  private final TourStartTimeService tourStartTimeService;
  private final LocationService locationService;
  private final UserService userService;
  private final CloudinaryUtil cloudinaryUtil;


  @Autowired
  public TourServiceImpl(
      ToResultInSearchSuggestionDtoConverter toResultInSearchSuggestionDtoConverter,
      CategoryService categoryService,
      TourStartTimeService tourStartTimeService,

      LocationService locationService ,UserService userService,CloudinaryUtil cloudinaryUtil,ImageRepository imageRepository) {
    this.toResultInSearchSuggestionDtoConverter = toResultInSearchSuggestionDtoConverter;


    this.categoryService = categoryService;
    this.tourStartTimeService = tourStartTimeService;
    this.locationService = locationService;
    this.userService = userService;
    this.cloudinaryUtil = cloudinaryUtil;
    this.imageRepository = imageRepository;
  }

  @Autowired
  public void setTourRepository(TourRepository tourRepository) {
    this.tourRepository = tourRepository;
  }

  @Override
  public List<TourDTO> getListTour() {
    return tourRepository.findAll().stream()
        .filter(tour -> tour.getIsDeleted().equals(false))
        .map(tourToTourDtoConverter::convert)
        .collect(Collectors.toList());
  }

  @Override
  public Tour saveTour(TourRequestDTO tourRequestDTO, String email) {
    Tour newTour = new Tour();
    User guide = userService.findUserByEmail(email);

    BeanUtils.copyProperties(tourRequestDTO, newTour, "categories","images");
    tourRequestDTO.getCategories().stream()
            .forEach(
                    category -> {
                      newTour.getCategories().add(categoryService.getCategoryById(category.getId()));
                    });
    if (guide != null) {
      newTour.setGuide(guide);
    }
    Tour tour = tourRepository.save(newTour);
    tourRequestDTO.getImages().stream().forEach(image->{
      Image imageTour = new Image();
      try {
        imageTour.setImageLink(cloudinaryUtil.uploadFile(image, FolderName.tour));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      imageTour.setAssociateId(tour.getId());
      imageTour.setAssociateName(AssociateName.TOUR);
      imageRepository.save(imageTour);

    });
    return  tourRepository.save(tour);
  }

  @Transactional
  @Override
  public Tour updateTour(UpdateTourRequestDTO updateTourRequestDTO) {
    Optional<Tour> optionalTour = tourRepository.findById(updateTourRequestDTO.id());
    if (optionalTour.isPresent()) {
      Tour tour = optionalTour.get();
      BeanUtils.copyProperties(
          updateTourRequestDTO,
          tour,
          "province",
          "categories",
          "tourStartTimes",
          "meetingPoint",
          "locations");

      // Update tour start time
      if (updateTourRequestDTO.tourStartTimes() != null) {
        tour.getTourStartTimes()
            .forEach(tourStartTime -> tourStartTimeService.deleteById(tourStartTime.getId()));
        tour.getTourStartTimes().clear();
        updateTourRequestDTO
            .tourStartTimes()
            .forEach(
                tourStartTime -> {
                  tourStartTime.setTour(tour);
                  tour.getTourStartTimes().add(tourStartTime);
                });
      }

      // Update category
      if (updateTourRequestDTO.category_ids() != null) {
        boolean isUpdateCategory =
            !tour.getCategories().stream()
                .map(Category::getId)
                .sorted()
                .collect(Collectors.toList())
                .equals(
                    updateTourRequestDTO.category_ids().stream()
                        .sorted()
                        .collect(Collectors.toList()));
        if (isUpdateCategory) {
          tour.getCategories().clear();
          updateTourRequestDTO
              .category_ids()
              .forEach(
                  categoryId -> {
                    tour.getCategories().add(categoryService.getCategoryById(categoryId));
                  });
        }
      }

      // TODO Update image

      // Update locations
      if (updateTourRequestDTO.locations() != null) {
        tour.getLocations().clear();
        updateTourRequestDTO
            .locations()
            .forEach(
                location -> {
                  if (location.getId() != null)
                    tour.getLocations().add(locationService.findById(location.getId()));
                  else
                    tour.getLocations()
                        .add(
                            new Location(
                                location.getName(),
                                location.getLatitude(),
                                location.getLongitude()));
                });
      }

      // Update meetingPoint
      if (updateTourRequestDTO.meetingPoint() != null
          && updateTourRequestDTO.meetingPoint().getId() == null) {
        Location newMeetingPoint =
            new Location(
                updateTourRequestDTO.meetingPoint().getName(),
                updateTourRequestDTO.meetingPoint().getLatitude(),
                updateTourRequestDTO.meetingPoint().getLongitude());
        locationService.save(newMeetingPoint);
        tour.setMeetingPoint(newMeetingPoint);
      }

      // Update province
      // TODO: Update here because province has change to String type
      // if (updateTourRequestDTO.province() != null
      //     && updateTourRequestDTO.province().getId() == null) {
      //   Location newProvince =
      //       new Location(
      //           updateTourRequestDTO.province().getName(),
      //           updateTourRequestDTO.province().getLatitude(),
      //           updateTourRequestDTO.province().getLongitude());
      //   locationService.save(newProvince);
      //   tour.setProvince(newProvince);
      // }
      return tourRepository.save(tour);
    }
    return null;
  }

  @Override
  public TourDTO getTourById(Long id) {
    return tourToTourDtoConverter.convert(tourRepository.findById(id).orElseThrow());
  }

  @Override
  public SearchTourDTO getTours(
      Integer page,
      Integer limit,
      String sortBy,
      String order,
      String searchKey,
      Double minPrice,
      Double maxPrice,
      List<Long> categoryId) {
    Sort sort = order.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    Pageable paging = PageRequest.of(page, limit, sort);
    Page<Tour> tourPage =
        tourRepository.findTours(searchKey, minPrice, maxPrice, categoryId, paging);
    List<TourDTO> tourDTOS =
        tourPage.get().map(tourToTourDtoConverter::convert).collect(Collectors.toList());
    return new SearchTourDTO(tourDTOS, tourPage.getTotalPages(), (int) tourPage.getTotalElements());
  }

  @Override
  public SearchTourDTO getToursByNameAndAddress(Integer page, Integer limit, String sortBy, String order, List<String> searchNames, List<String> addresses, Double minPrice, Double maxPrice, List<Long> categoryId) {
    Sort sort = order.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    Pageable paging = PageRequest.of(page, limit, sort);
    Page<Tour> tourPage =
            tourRepository.findToursByNameAndAddress(searchNames, minPrice, maxPrice, categoryId, paging,addresses);
    return null;
  }

  @Override
  public List<TourDTO> deleteTour(Long id) {
    Tour tour = tourRepository.findById(id).orElseThrow();
    tour.setIsDeleted(true);
    tourRepository.save(tour);
    return tourRepository.findAll().stream()
        .map(tourToTourDtoConverter::convert)
        .collect(Collectors.toList());
  }

  @Override
  public List<Tour> getToursOfGuide(String email) {
    return tourRepository.findAll().stream()
        .filter(
            tour -> tour.getIsDeleted().equals(false) && tour.getGuide().getEmail().equals(email))
        .collect(Collectors.toList());
  }

  @Override
  public SearchSuggestionResponseDTO getTourAndTourAddresses(String searchValue) {
    List<Tour> tours = tourRepository.findAll();
    List<String> addressesFiltered =
        tours.stream()
            .map(tour -> AddressUtils.removeVietnameseAccents(tour.getAddress()))
            .distinct()
            .filter(
                address ->
                    AddressUtils.removeVietnameseAccents(address)
                        .toLowerCase()
                        .contains(AddressUtils.removeVietnameseAccents(searchValue).toLowerCase()))
            .toList();
    List<ResultInSearchSuggestionDTO> guidersFiltered =
        tours.stream()
            .sorted(Comparator.comparing(Tour::getOverallRating).reversed())
            .filter(
                tour ->
                    AddressUtils.removeVietnameseAccents(tour.getName())
                        .toLowerCase()
                        .contains(AddressUtils.removeVietnameseAccents(searchValue).toLowerCase()))
            .map(toResultInSearchSuggestionDtoConverter::convert)
            .toList();
    return new SearchSuggestionResponseDTO(addressesFiltered, guidersFiltered);
  }
}
