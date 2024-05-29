package com.application.winelibrary.repository.food;

import com.application.winelibrary.entity.Food;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FoodRepository extends JpaRepository<Food, Long> {
    @Query("SELECT food FROM Food food WHERE food.name IN :foodNames")
    Set<Food> findFoodsByNames(Set<Food.FoodName> foodNames);
}
