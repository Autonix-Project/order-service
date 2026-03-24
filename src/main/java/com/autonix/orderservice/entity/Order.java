package com.autonix.orderservice.entity;

import com.autonix.orderservice.enumtype.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "order_number", nullable = false, unique = true)
    private String orderNumber;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "car_color")
    private String carColor;

    @Column(name = "car_model")
    private String carModel;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Order(Long memberId,
                 String orderNumber,
                 String destination,
                 OrderStatus status,
                 LocalDateTime deadline,
                 String carColor,
                 String carModel,
                 Integer totalQuantity) {
        this.memberId = memberId;
        this.orderNumber = orderNumber;
        this.destination = destination;
        this.status = status;
        this.deadline = deadline;
        this.carColor = carColor;
        this.carModel = carModel;
        this.totalQuantity = totalQuantity;
    }

    public void update(String destination,
                       LocalDateTime deadline,
                       String carColor,
                       String carModel,
                       Integer totalQuantity) {
        this.destination = destination;
        this.deadline = deadline;
        this.carColor = carColor;
        this.carModel = carModel;
        this.totalQuantity = totalQuantity;
    }

    public void startProduction() {
        this.status = OrderStatus.IN_PROGRESS;
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = OrderStatus.READY;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}