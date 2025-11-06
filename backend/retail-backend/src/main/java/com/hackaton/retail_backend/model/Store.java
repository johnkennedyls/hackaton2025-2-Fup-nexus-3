package com.hackaton.retail_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "store")
@Data
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;
}
