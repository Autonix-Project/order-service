package com.autonix.orderservice.entity;

import com.autonix.orderservice.enumtype.CarType;
import com.autonix.orderservice.enumtype.ColorType;
import com.autonix.orderservice.enumtype.DestinationType;
import com.autonix.orderservice.enumtype.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_code", unique = true)
    private String orderCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarType carType;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ColorType color;

    @Column(nullable = false)
    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DestinationType destination;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Order(CarType carType,
                 Integer quantity,
                 ColorType color,
                 LocalDate deadline,
                 DestinationType destination,
                 OrderStatus status) {
        this.carType = carType;
        this.quantity = quantity;
        this.color = color;
        this.deadline = deadline;
        this.destination = destination;
        this.status = status;
    }

    public void assignOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}