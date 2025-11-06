package com.hackaton.retail_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Generated;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_item")
@Data
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "size_id", nullable = false)
    private Size size;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Generated
    @Column(name = "subtotal", updatable = false, insertable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;
}
