package com.application.winelibrary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.SoftDelete;

@Entity
@Table(name = "orders")
@SoftDelete
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "delivery_type_id", nullable = false)
    private Delivery deliveryType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", columnDefinition = "varchar", nullable = false)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar", nullable = false)
    private Status status;

    @Column(nullable = false)
    private BigDecimal orderAmount;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @OneToMany(mappedBy = "order")
    @Cascade(CascadeType.ALL)
    private Set<OrderItem> orderItems;

    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;

    public enum PaymentType {
        CARD,
        CASH
    }

    public enum Status {
        PENDING,
        DELIVERED,
        COMPLETED
    }
}
