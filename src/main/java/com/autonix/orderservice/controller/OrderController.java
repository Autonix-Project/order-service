package com.autonix.orderservice.controller;

import com.autonix.orderservice.dto.request.CreateOrderRequest;
import com.autonix.orderservice.dto.request.UpdateOrderRequest;
import com.autonix.orderservice.dto.response.OrderResponse;
import com.autonix.orderservice.global.response.ApiResponse;
import com.autonix.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return ApiResponse.ok("주문 생성 성공", orderService.createOrder(request));
    }

    @GetMapping
    public ApiResponse<List<OrderResponse>> getOrders() {
        return ApiResponse.ok("주문 목록 조회 성공", orderService.getOrders());
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrder(@PathVariable Long orderId) {
        return ApiResponse.ok("주문 단건 조회 성공", orderService.getOrder(orderId));
    }

    @PatchMapping("/{orderId}")
    public ApiResponse<OrderResponse> updateOrder(@PathVariable Long orderId,
                                                  @Valid @RequestBody UpdateOrderRequest request) {
        return ApiResponse.ok("주문 수정 성공", orderService.updateOrder(orderId, request));
    }

    @PostMapping("/{orderId}/start")
    public ApiResponse<OrderResponse> startOrder(@PathVariable Long orderId) {
        return ApiResponse.ok("주문 생산 시작 성공", orderService.startOrder(orderId));
    }

    @GetMapping("/test")
    public String test() {
        return "order-service ok";
    }
}