package com.application.winelibrary.repository.city;

import com.application.winelibrary.entity.City;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
    @EntityGraph(attributePaths = {"ukrPostOffices", "novaPostOffices"})
    Optional<City> findByName(String name);
}
