package com.hackaton.retail_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "gender")
@Data
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 20)
    private String name;
}
