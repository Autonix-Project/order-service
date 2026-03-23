package com.autonix.orderservice.dto.request;

import com.autonix.orderservice.enumtype.CarType;
import com.autonix.orderservice.enumtype.ColorType;
import com.autonix.orderservice.enumtype.DestinationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CreateOrderRequest {

    @NotNull(message = "차종은 필수입니다.")
    private CarType carType;

    @NotNull(message = "수량은 필수입니다.")
    @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    private Integer quantity;

    @NotNull(message = "색상은 필수입니다.")
    private ColorType color;

    @NotNull(message = "마감기한은 필수입니다.")
    private LocalDate deadline;

    @NotNull(message = "목적지는 필수입니다.")
    private DestinationType destination;
}