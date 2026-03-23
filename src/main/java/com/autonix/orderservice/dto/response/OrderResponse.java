package com.autonix.orderservice.dto.response;

import com.autonix.orderservice.entity.Order;
import com.autonix.orderservice.enumtype.CarType;
import com.autonix.orderservice.enumtype.ColorType;
import com.autonix.orderservice.enumtype.DestinationType;
import com.autonix.orderservice.enumtype.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class OrderResponse {

    private Long id;
    private String orderCode;
    private CarType carType;
    private Integer quantity;
    private ColorType color;
    private LocalDate deadline;
    private DestinationType destination;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderCode(order.getOrderCode())
                .carType(order.getCarType())
                .quantity(order.getQuantity())
                .color(order.getColor())
                .deadline(order.getDeadline())
                .destination(order.getDestination())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}