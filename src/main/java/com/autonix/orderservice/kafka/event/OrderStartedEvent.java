package com.autonix.orderservice.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStartedEvent {

    private Long orderId;
    private String orderNumber;
    private String carModel;
    private String carColor;
    private Integer totalQuantity;
}