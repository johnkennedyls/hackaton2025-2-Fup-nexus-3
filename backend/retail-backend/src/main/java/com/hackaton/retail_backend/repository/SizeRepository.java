package com.hackaton.retail_backend.repository;

import com.hackaton.retail_backend.model.Size;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    Optional<Size> findByName(@NotNull String sizeName);
}

