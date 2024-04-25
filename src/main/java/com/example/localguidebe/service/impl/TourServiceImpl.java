package com.example.localguidebe.service.impl;

import com.example.localguidebe.converter.ToResultInSearchSuggestionDtoConverter;
import com.example.localguidebe.converter.TourToTourDtoConverter;
import com.example.localguidebe.dto.LocationDTO;
import com.example.localguidebe.dto.TourDTO;
import com.example.localguidebe.dto.requestdto.InfoLocationDTO;
import com.example.localguidebe.dto.requestdto.TourRequestDTO;
import com.example.localguidebe.dto.requestdto.UpdateTourRequestDTO;
import com.example.localguidebe.dto.responsedto.ResultInSearchSuggestionDTO;
import com.example.localguidebe.dto.responsedto.SearchSuggestionResponseDTO;
import com.example.localguidebe.dto.responsedto.SearchTourDTO;
import com.example.localguidebe.entity.*;
import com.example.localguidebe.enums.AssociateName;
import com.example.localguidebe.enums.BookingStatusEnum;
import com.example.localguidebe.enums.FolderName;
import com.example.localguidebe.repository.*;
import com.example.localguidebe.repository.ImageRepository;
import com.example.localguidebe.repository.TourRepository;
import com.example.localguidebe.service.*;
import com.example.localguidebe.service.CategoryService;
import com.example.localguidebe.service.LocationService;
import com.example.localguidebe.service.TourService;
import com.example.localguidebe.service.TourStartTimeService;
import com.example.localguidebe.utils.AddressUtils;
import com.example.localguidebe.utils.CloudinaryUtil;
import com.google.gson.Gson;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
  private final TourToTourDtoConverter tourToTourDtoConverter;
  private final ToResultInSearchSuggestionDtoConverter toResultInSearchSuggestionDtoConverter;
  private final TourStartTimeRepository tourStartTimeRepository;
  private final ImageRepository imageRepository;
  private TourRepository tourRepository;
  private final CategoryService categoryService;
  private final TourStartTimeService tourStartTimeService;
  private final LocationService locationService;
  private final UserService userService;
  private final CloudinaryUtil cloudinaryUtil;
  private final GeoCodingService geoCodingService;

  private final BookingRepository bookingRepository;
  private final Gson gson = new Gson();

  @Autowired
  public TourServiceImpl(
      ToResultInSearchSuggestionDtoConverter toResultInSearchSuggestionDtoConverter,
      TourStartTimeRepository tourStartTimeRepository,
      CategoryService categoryService,
      TourStartTimeService tourStartTimeService,
      LocationService locationService,
      UserService userService,
      CloudinaryUtil cloudinaryUtil,
      ImageRepository imageRepository,
      BookingRepository bookingRepository,
      TourToTourDtoConverter tourToTourDtoConverter,
      GeoCodingService geoCodingService) {
    this.tourStartTimeRepository = tourStartTimeRepository;
    this.toResultInSearchSuggestionDtoConverter = toResultInSearchSuggestionDtoConverter;
    this.geoCodingService = geoCodingService;
    this.categoryService = categoryService;
    this.tourStartTimeService = tourStartTimeService;
    this.locationService = locationService;
    this.userService = userService;
    this.cloudinaryUtil = cloudinaryUtil;
    this.imageRepository = imageRepository;
    this.tourToTourDtoConverter = tourToTourDtoConverter;
    this.bookingRepository = bookingRepository;
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
    Gson gson = new Gson();
    BeanUtils.copyProperties(tourRequestDTO, newTour, "categories", "images", "locations");
  // add tour start time
    tourRequestDTO
        .getStartTimes()
        .forEach(
                getStartTime ->
                newTour
                    .getTourStartTimes()
                    .add(TourStartTime.builder().startTime(getStartTime).build()));
  // add location for tour
    if (tourRequestDTO.getLocations().size() != 0) {
      tourRequestDTO
          .getLocations()
          .forEach(
              locationDTO -> {
                InfoLocationDTO infoLocationDTO =
                    gson.fromJson(
                        geoCodingService.getAddress(
                            locationDTO.latitude(), locationDTO.longitude()),
                        InfoLocationDTO.class);
                newTour
                    .getLocations()
                    .add(
                        Location.builder()
                            .address(infoLocationDTO.getDisplay_name())
                            .name(locationDTO.name())
                            .latitude(locationDTO.latitude())
                            .longitude(locationDTO.longitude())
                            .build());
              });
    }
    //add guider for tour
    tourRequestDTO.getCategories()
        .forEach(
            category -> newTour.getCategories().add(categoryService.getCategoryById(category.getId())));
    if (guide != null) {
      newTour.setGuide(guide);
    }
    Tour tour = tourRepository.save(newTour);
    tourRequestDTO.getImages()
        .forEach(
            image -> {
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
    return tourRepository.save(tour);
  }

  @Transactional
  @Override
  public Tour updateTour(UpdateTourRequestDTO updateTourRequestDTO) {
    Optional<Tour> optionalTour = tourRepository.findById(updateTourRequestDTO.id());
    if (optionalTour.isPresent()) {
      Tour tour = optionalTour.get();
      BeanUtils.copyProperties(
          updateTourRequestDTO, tour, "categories", "tourStartTimes", "meetingPoint", "locations");

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
                .toList()
                .equals(
                    updateTourRequestDTO.category_ids().stream()
                        .sorted()
                        .collect(Collectors.toList()));
        if (isUpdateCategory) {
          tour.getCategories().clear();
          updateTourRequestDTO
              .category_ids()
              .forEach(
                  categoryId -> tour.getCategories().add(categoryService.getCategoryById(categoryId)));
        }
      }

      // Update images
      List<Image> tourImages =
          imageRepository.getImageByAssociateIddAndAssociateName(
              updateTourRequestDTO.id(), AssociateName.TOUR);
      int maxSize = Math.max(tourImages.size(), updateTourRequestDTO.image_ids().size());
      for (int i = 0; i < maxSize; i++) {
        if (i < updateTourRequestDTO.image_ids().size()
            && updateTourRequestDTO.image_ids().get(i).startsWith("data:")) {
          Image imageTour = new Image();
          try {
            imageTour.setImageLink(
                cloudinaryUtil.uploadFile(
                    updateTourRequestDTO.image_ids().get(i), FolderName.tour));
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
          imageTour.setAssociateId(tour.getId());
          imageTour.setAssociateName(AssociateName.TOUR);
          imageRepository.save(imageTour);
        }

        if (i < tourImages.size()
            && !updateTourRequestDTO.image_ids().contains(tourImages.get(i).getId().toString())) {
          if (cloudinaryUtil.deleteFile(tourImages.get(i).getImageLink())) {
            imageRepository.deleteById(tourImages.get(i).getId());
          }

        }
      }

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
  public List<TourDTO> deleteTour(Long id) {
    Tour tour = tourRepository.findById(id).orElseThrow();
    tour.setIsDeleted(true);
    tourRepository.save(tour);
    return tourRepository.findAll().stream()
        .map(tourToTourDtoConverter::convert)
        .collect(Collectors.toList());
  }

  @Override
  public List<Tour> getToursOfGuide(Long guideId) {
    return tourRepository.findAll().stream()
        .filter(
            tour -> tour.getIsDeleted().equals(false) && tour.getGuide().getId().equals(guideId))
        .collect(Collectors.toList());
  }

  @Override
  public SearchSuggestionResponseDTO getTourAndTourLocations(String searchValue) {
    List<Tour> tours = tourRepository.findAll();
    List<String> addressesFiltered =
        tours.stream()
            .flatMap(tour -> tour.getLocations().stream().map(Location::getAddress))
            .filter(
                address ->
                    AddressUtils.removeVietnameseAccents(address)
                        .toLowerCase()
                        .contains(AddressUtils.removeVietnameseAccents(searchValue).toLowerCase()))
            .distinct()
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

  @Override
  public List<String> getTourStartTimeAvailable(Long tourId, LocalDate localDate) {
    List<String> tourStartTimes = tourStartTimeRepository.findByTourId(tourId);
    List<String> startDateTimesInBooKing =
        bookingRepository.findStartDateTimesByTourIdAndStartDate(tourId, localDate);
    List<String> tourStartTimesAvailable = new ArrayList<>(tourStartTimes);
    tourStartTimesAvailable.removeAll(startDateTimesInBooKing);
    return tourStartTimesAvailable;
  }

  @Override
  public boolean checkBookingByTraveler(Long tourId, String email) {
    User traveler = userService.findUserByEmail(email);
    return bookingRepository.findAll().stream()
        .anyMatch(
            booking ->
                Objects.equals(booking.getTour().getId(), tourId)
                    && Objects.equals(booking.getCart().getTraveler().getId(), traveler.getId())
                    && booking.getStatus().equals(BookingStatusEnum.PAID));
  }

  @Override
  public void updateRatingForTour(Tour tour) {
    tour.setOverallRating(
        tour.getReviews().stream()
            .map(Review::getRating)
            .filter(rating -> rating > 0)
            .mapToInt(Integer::intValue)
            .average()
            .orElse(0.0));
    tourRepository.save(tour);
  }

  @Override
  public List<String> getLocationName(List<LocationDTO> locationDTOS) {
    List<String> locationName  = new ArrayList<>();
    locationDTOS.stream().forEach(location -> locationName.add(gson.fromJson(
            geoCodingService.getAddress(
                    location.latitude(), location.longitude()),
            InfoLocationDTO.class).getName())
    );
    return locationName;
  }
}
