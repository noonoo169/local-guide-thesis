package com.example.localguidebe.service.impl;

import com.example.localguidebe.dto.responsedto.StatisticalGuideDTO;
import com.example.localguidebe.dto.responsedto.StatisticalTourDTO;
import com.example.localguidebe.entity.Tour;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.enums.RolesEnum;
import com.example.localguidebe.repository.BookingRepository;
import com.example.localguidebe.repository.TourRepository;
import com.example.localguidebe.repository.UserRepository;
import com.example.localguidebe.service.StatisticService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {
    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final TourRepository tourRepository;

    public StatisticServiceImpl(
            BookingRepository bookingRepository,
            UserRepository userRepository,
            TourRepository tourRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
    }

    // get statistic of all guide
    @Override
    public List<StatisticalGuideDTO> getStatisticalByGuide(
    ) {
        List<User> guides = userRepository.findByRoles_Name(RolesEnum.GUIDER);
        List<StatisticalGuideDTO> statisticalGuideDTOS = new ArrayList<>();
        for (User guide : guides) {
            statisticalGuideDTOS.add(getStatisticByPerGuide(guide.getId()));
        }

        return statisticalGuideDTOS;
    }

    // get revenue of per guide
    @Override
    public Double getRevenueByGuide(Long guideId) {
        double revenueOfGuide = 0.0;
        List<Long> tourIdOfGuides = bookingRepository.getTourIdByGuide(guideId);
        for (Long tourIdOfGuide : tourIdOfGuides) {
            revenueOfGuide +=
                    getRevenueByTour(tourIdOfGuide) != null ? getRevenueByTour(tourIdOfGuide) : 0L;
        }
        return revenueOfGuide;
    }

    // get revenue of per tour
    @Override
    public Double getRevenueByTour(Long tourId) {
        return bookingRepository.getRevenueByTourId(tourId);
    }

    // get total traveler number booked by tour
    @Override
    public Long getTotalTravelerNumberByTour(Long tourId) {
        return bookingRepository.getTotalTravelerNumberByTour(tourId);
    }

    // get total traveler number booked by guide
    @Override
    public Long getTotalBookingByGuide(Long guideId) {
        Long totalBookingNumbers = 0L;
        List<Long> tourIdOfGuides = bookingRepository.getTourIdByGuide(guideId);
        for (Long tourIdOfGuide : tourIdOfGuides) {
            totalBookingNumbers +=
                    getTotalBookingByTour(tourIdOfGuide) != null ? getTotalBookingByTour(tourIdOfGuide) : 0L;
        }
        return totalBookingNumbers;
    }

    // get statistic of per tour
    @Override
    public StatisticalTourDTO getStatisticByPerTour(Long tourId) {
        Tour tour = tourRepository.findById(tourId).orElseThrow();
        StatisticalTourDTO statisticalTourDTO =
                StatisticalTourDTO.builder()
                        .id(tour.getId())
                        .name(tour.getName())
                        .limitTraveler(tour.getLimitTraveler())
                        .pricePerTraveler(tour.getPricePerTraveler())
                        .extraPrice(tour.getExtraPrice())
                        .overallRating(tour.getOverallRating() != null ? tour.getOverallRating() : 0.0)
                        .totalTravelerNumber(
                                getTotalTravelerNumberByTour(tour.getId()) != null
                                        ? getTotalTravelerNumberByTour(tour.getId())
                                        : 0)
                        .totalRevenue(
                                getRevenueByTour(tour.getId()) != null ? getRevenueByTour(tour.getId()) : 0.0)
                        .totalBooking(getTotalBookingByTour(tour.getId()))
                        .build();
        return statisticalTourDTO;
    }

    // get statistic of per guide
    @Override
    public StatisticalGuideDTO getStatisticByPerGuide(Long guideId) {
        User guide = userRepository.findById(guideId).orElseThrow();
        StatisticalGuideDTO statisticalGuideDTO =
                StatisticalGuideDTO.builder()
                        .id(guide.getId())
                        .phone(guide.getPhone())
                        .dateOfBirth(guide.getDateOfBirth())
                        .email(guide.getEmail())
                        .fullName(guide.getFullName())
                        .overallRating(guide.getOverallRating() != null ? guide.getOverallRating() : 0.0)
                        .address(guide.getAddress())
                        .totalRevenue(getRevenueByGuide(guide.getId()))
                        .totalTravelerNumber(getTotalTravelerNumberByGuide(guide.getId()))
                        .totalBooking(getTotalBookingByGuide(guide.getId()))
                        .build();
        return statisticalGuideDTO;
    }

    // get statistic of tours by guide
    @Override
    public List<StatisticalTourDTO> getStatisticOfToursByGuide(
            Long guideId) {
        List<StatisticalTourDTO> statisticOfToursByGuide = new ArrayList<>();

        List<Long> tourIdOfGuidesPage = bookingRepository.getTourIdByGuide(guideId);
        for (Long tourIdOfGuide : tourIdOfGuidesPage.stream().toList()) {
            statisticOfToursByGuide.add(getStatisticByPerTour(tourIdOfGuide));
        }

        return statisticOfToursByGuide;
    }

    // get total traveler number by guide
    @Override
    public Long getTotalTravelerNumberByGuide(Long guideId) {
        long totalTravelerNumbers = 0L;
        List<Long> tourIdOfGuides = bookingRepository.getTourIdByGuide(guideId);
        for (Long tourIdOfGuide : tourIdOfGuides) {
            totalTravelerNumbers +=
                    getTotalTravelerNumberByTour(tourIdOfGuide) != null
                            ? getTotalTravelerNumberByTour(tourIdOfGuide)
                            : 0;
        }
        return totalTravelerNumbers;
    }

    // get statistic of all tours
    @Override
    public List<StatisticalTourDTO> getStatisticalByTour() {
        List<Tour> tours = tourRepository.findAll();
        List<StatisticalTourDTO> statisticalTourDTOS = new ArrayList<>();
        for (Tour tour : tours) {
            statisticalTourDTOS.add(getStatisticByPerTour(tour.getId()));
        }
        return statisticalTourDTOS;
    }

    // get total booking  of per tour
    @Override
    public Long getTotalBookingByTour(Long tourId) {
        return bookingRepository.getTotalBookingByTour(tourId);
    }
}
