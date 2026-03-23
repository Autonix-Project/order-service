package com.autonix.orderservice.event;

import com.autonix.orderservice.enumtype.CarType;
import com.autonix.orderservice.enumtype.ColorType;
import com.autonix.orderservice.enumtype.DestinationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStartedEvent {

    private Long orderId;
    private String orderCode;
    private CarType carType;
    private Integer quantity;
    private ColorType color;
    private LocalDate deadline;
    private DestinationType destination;
    private List<String> vehicleCodes;
}