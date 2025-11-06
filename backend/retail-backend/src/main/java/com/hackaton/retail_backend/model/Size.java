package com.hackaton.retail_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "size")
@Data
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 10)
    private String name;

    @Column(name = "size_order", nullable = false)
    private Integer sizeOrder;
}
