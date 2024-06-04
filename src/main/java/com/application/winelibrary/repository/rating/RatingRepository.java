package com.application.winelibrary.repository.rating;

import com.application.winelibrary.entity.Rating;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findAllByWineId(Long wineId);
}
