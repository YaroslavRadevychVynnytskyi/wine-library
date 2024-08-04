package com.application.winelibrary.repository.wine;

import com.application.winelibrary.entity.Wine;
import com.application.winelibrary.repository.custom.CustomRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WineRepository extends JpaRepository<Wine, Long>, JpaSpecificationExecutor<Wine>,
        CustomRepository<Wine> {
    @EntityGraph(attributePaths = {"recommendedFood", "comments", "comments.user"})
    Page<Wine> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"recommendedFood", "comments", "comments.user"})
    List<Wine> findAll(Specification<Wine> spec);

    @EntityGraph(attributePaths = {"recommendedFood", "comments", "comments.user",
            "ratings", "ratings.user"})
    Optional<Wine> findById(Long id);
}
