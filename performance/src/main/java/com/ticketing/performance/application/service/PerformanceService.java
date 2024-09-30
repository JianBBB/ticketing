package com.ticketing.performance.application.service;

import com.ticketing.performance.application.dto.hall.HallInfoResponseDto;
import com.ticketing.performance.application.dto.performance.PrfInfoResponseDto;
import com.ticketing.performance.application.dto.performance.PrfListResponseDto;
import com.ticketing.performance.application.dto.seat.SeatInfoResponseDto;
import com.ticketing.performance.domain.model.Performance;
import com.ticketing.performance.domain.model.SeatStatus;
import com.ticketing.performance.domain.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final SeatService seatService;
    private final HallService hallService;

    public Page<PrfListResponseDto> getPerformances(Pageable pageable) {
        return performanceRepository.findAll(pageable).map(PrfListResponseDto::of);
    }

    public PrfInfoResponseDto getPerformance(UUID performanceId) {
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new RuntimeException("error"));

        HallInfoResponseDto hall = hallService.getHall(performance.getHallId());


        List<SeatInfoResponseDto> seatList = seatService.getSeats(performanceId);
        int totalSeat = seatList.size();
        int availableSeat = seatList.stream()
                .filter(seat -> seat.getSeatStatus() == SeatStatus.AVAILABLE)
                .toList()
                .size();

        return PrfInfoResponseDto.of(performance, hall.getHallName(), totalSeat, availableSeat);
    }

}
