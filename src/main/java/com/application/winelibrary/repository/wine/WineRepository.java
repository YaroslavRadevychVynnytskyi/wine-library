package com.application.winelibrary.repository.wine;

import com.application.winelibrary.entity.Wine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WineRepository extends JpaRepository<Wine, Long> {
    @EntityGraph(attributePaths = {"recommendedFood"})
    Page<Wine> findAll(Pageable pageable);
}
