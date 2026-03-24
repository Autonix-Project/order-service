package com.autonix.orderservice.kafka.producer;

import com.autonix.orderservice.entity.Order;
import com.autonix.orderservice.kafka.event.OrderStartedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrderStartedEvent(Order order) {

        OrderStartedEvent event = OrderStartedEvent.builder()
                .orderId(order.getOrderId())
                .orderNumber(order.getOrderNumber())
                .carModel(order.getCarModel())
                .carColor(order.getCarColor())
                .totalQuantity(order.getTotalQuantity())
                .build();

        kafkaTemplate.send("order.start", event.getOrderNumber(), event);

        System.out.println("🔥 Kafka 이벤트 전송: " + event.getOrderNumber());
    }
}