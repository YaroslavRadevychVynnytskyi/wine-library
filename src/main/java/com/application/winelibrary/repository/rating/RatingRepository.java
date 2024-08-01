package com.application.winelibrary.repository.rating;

import com.application.winelibrary.entity.Rating;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    @EntityGraph(attributePaths = {"wine", "user"})
    List<Rating> findAllByWineId(Long wineId);

    Optional<Rating> findByWineIdAndUserId(Long wineId, Long userId);
}
