package com.ticketing.performance.application.dto.seat;

import com.ticketing.performance.common.util.SecurityUtil;
import com.ticketing.performance.domain.model.Seat;
import com.ticketing.performance.domain.model.SeatStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SeatInfoResponseDto implements Serializable {

    private UUID seatId;
    private UUID performanceId;
    private UUID orderId;
    private String seatType;
    private Integer seatRow;
    private Integer seatNum;
    private Integer price;
    private SeatStatus seatStatus;
    private Long userId;

    public static SeatInfoResponseDto of(Seat seat) {
        return SeatInfoResponseDto.builder()
                .seatId(seat.getId())
                .performanceId(seat.getPerformanceId())
                .orderId(seat.getOrderId())
                .seatType(seat.getSeatType())
                .seatRow(seat.getSeatRow())
                .seatNum(seat.getSeatNum())
                .price(seat.getPrice())
                .seatStatus(seat.getSeatStatus())
                .build();
    }

    public void confirm(UUID orderId) {
        this.orderId = orderId;
        this.seatStatus = SeatStatus.BOOKED;

    }

    public void hold() {
        this.userId = SecurityUtil.getId();
        this.seatStatus = SeatStatus.HOLD;
    }

    public void cancel() {
        this.orderId = null;
        this.seatStatus = SeatStatus.AVAILABLE;
        this.userId = null;
    }


}
