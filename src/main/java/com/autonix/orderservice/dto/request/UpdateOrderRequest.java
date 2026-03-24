package com.autonix.orderservice.dto.request;

import com.autonix.orderservice.enumtype.CarType;
import com.autonix.orderservice.enumtype.ColorType;
import com.autonix.orderservice.enumtype.DestinationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UpdateOrderRequest {

    private String destination;
    private LocalDateTime deadline;
    private String carColor;
    private String carModel;
    private Integer totalQuantity;
}