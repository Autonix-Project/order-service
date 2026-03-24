package com.autonix.orderservice.dto.request;

import com.autonix.orderservice.enumtype.CarType;
import com.autonix.orderservice.enumtype.ColorType;
import com.autonix.orderservice.enumtype.DestinationType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CreateOrderRequest {

    private CarType carType;
    private ColorType color;
    private DestinationType destination;
    private Integer totalQuantity;
    private LocalDateTime deadline;
}
