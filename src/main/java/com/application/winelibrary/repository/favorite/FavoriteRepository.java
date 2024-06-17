package com.application.winelibrary.repository.favorite;

import com.application.winelibrary.entity.Favorite;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserIdAndWineId(Long userId, Long wineId);

    @EntityGraph(attributePaths = {"wine"})
    List<Favorite> findByUserId(Long userId);

    void deleteByWineId(Long wineId);
}
