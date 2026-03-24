package com.autonix.orderservice.dto.response;

import com.autonix.orderservice.entity.Order;
import com.autonix.orderservice.enumtype.CarType;
import com.autonix.orderservice.enumtype.ColorType;
import com.autonix.orderservice.enumtype.DestinationType;
import com.autonix.orderservice.enumtype.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private String orderNumber;
    private Long memberId;
    private String destination;
    private String status;
    private LocalDateTime deadline;
    private String carColor;
    private String carModel;
    private Integer totalQuantity;

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .orderNumber(order.getOrderNumber())
                .memberId(order.getMemberId())
                .destination(order.getDestination())
                .status(order.getStatus().name())
                .deadline(order.getDeadline())
                .carColor(order.getCarColor())
                .carModel(order.getCarModel())
                .totalQuantity(order.getTotalQuantity())
                .build();
    }
}