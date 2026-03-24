package com.autonix.orderservice.service;

import com.autonix.orderservice.dto.request.CreateOrderRequest;
import com.autonix.orderservice.dto.request.UpdateOrderRequest;
import com.autonix.orderservice.dto.response.OrderResponse;
import com.autonix.orderservice.entity.Order;
import com.autonix.orderservice.enumtype.OrderStatus;
import com.autonix.orderservice.global.exception.InvalidOrderStateException;
import com.autonix.orderservice.global.exception.OrderNotFoundException;
import com.autonix.orderservice.kafka.producer.OrderEventProducer;
import com.autonix.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventProducer producer;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        validateCreateRequest(request);

        Order order = Order.builder()
                .memberId(request.getMemberId())
                .destination(request.getDestination())
                .deadline(request.getDeadline())
                .carColor(request.getCarColor())
                .carModel(request.getCarModel())
                .totalQuantity(request.getTotalQuantity())
                .orderNumber(generateOrderNumber())
                .status(OrderStatus.READY)
                .build();

        Order saved = orderRepository.save(order);
        return OrderResponse.from(saved);
    }

    public List<OrderResponse> getOrders() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::from)
                .toList();
    }

    public OrderResponse getOrder(Long orderId) {
        return OrderResponse.from(find(orderId));
    }

    @Transactional
    public OrderResponse updateOrder(Long orderId, UpdateOrderRequest request) {
        Order order = find(orderId);

        if (order.getStatus() != OrderStatus.READY) {
            throw new InvalidOrderStateException("READY 상태에서만 수정 가능합니다.");
        }

        validateUpdateRequest(request);

        order.update(
                request.getDestination(),
                request.getDeadline(),
                request.getCarColor(),
                request.getCarModel(),
                request.getTotalQuantity()
        );

        return OrderResponse.from(order);
    }

    @Transactional
    public OrderResponse startOrder(Long orderId) {
        Order order = find(orderId);

        if (order.getStatus() != OrderStatus.READY) {
            throw new InvalidOrderStateException("READY 상태의 주문만 생산 시작할 수 있습니다.");
        }

        order.startProduction();
        producer.sendOrderStartedEvent(order);

        return OrderResponse.from(order);
    }

    private Order find(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    private void validateCreateRequest(CreateOrderRequest request) {
        if (request.getMemberId() == null) {
            throw new IllegalArgumentException("memberId는 필수입니다.");
        }
        if (request.getDestination() == null || request.getDestination().isBlank()) {
            throw new IllegalArgumentException("destination은 필수입니다.");
        }
        if (request.getCarColor() == null || request.getCarColor().isBlank()) {
            throw new IllegalArgumentException("carColor는 필수입니다.");
        }
        if (request.getCarModel() == null || request.getCarModel().isBlank()) {
            throw new IllegalArgumentException("carModel은 필수입니다.");
        }
        if (request.getTotalQuantity() == null || request.getTotalQuantity() < 1) {
            throw new IllegalArgumentException("totalQuantity는 1 이상이어야 합니다.");
        }
    }

    private void validateUpdateRequest(UpdateOrderRequest request) {
        if (request.getDestination() == null || request.getDestination().isBlank()) {
            throw new IllegalArgumentException("destination은 필수입니다.");
        }
        if (request.getCarColor() == null || request.getCarColor().isBlank()) {
            throw new IllegalArgumentException("carColor는 필수입니다.");
        }
        if (request.getCarModel() == null || request.getCarModel().isBlank()) {
            throw new IllegalArgumentException("carModel은 필수입니다.");
        }
        if (request.getTotalQuantity() == null || request.getTotalQuantity() < 1) {
            throw new IllegalArgumentException("totalQuantity는 1 이상이어야 합니다.");
        }
    }

    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
}