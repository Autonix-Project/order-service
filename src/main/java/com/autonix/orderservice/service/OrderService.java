package com.autonix.orderservice.service;

import com.autonix.orderservice.dto.request.CreateOrderRequest;
import com.autonix.orderservice.dto.response.OrderResponse;
import com.autonix.orderservice.entity.Order;
import com.autonix.orderservice.enumtype.OrderStatus;
import com.autonix.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        validateDeadline(request.getDeadline());

        Order order = Order.builder()
                .carType(request.getCarType())
                .quantity(request.getQuantity())
                .color(request.getColor())
                .deadline(request.getDeadline())
                .destination(request.getDestination())
                .status(OrderStatus.CREATED)
                .build();

        Order savedOrder = orderRepository.save(order);

        String orderCode = generateOrderCode(savedOrder.getId());
        savedOrder.assignOrderCode(orderCode);

        return OrderResponse.from(savedOrder);
    }

    public List<OrderResponse> getOrders() {
        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(OrderResponse::from)
                .toList();
    }

    private void validateDeadline(LocalDate deadline) {
        if (deadline.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("마감기한은 오늘 이전일 수 없습니다.");
        }
    }

    private String generateOrderCode(Long id) {
        return String.format("ORD-%03d", id);
    }
}