package com.osla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.osla.model.CurrentIngredient;
import com.osla.model.OutputIngredient;


@Repository
public interface CurrentIngredientRepository extends JpaRepository<CurrentIngredient, Integer> {
    public List<CurrentIngredient> findByName(String name);

    @Query("""
            SELECT new com.osla.model.OutputIngredient(ci.name, SUM(ci.count))
            FROM CurrentIngredient ci
            JOIN SavedIngredient si ON ci.name=si.name
            GROUP BY ci.name
            ORDER BY si.orderValue
            """)
    public List<OutputIngredient> getSummedOrderedIngredients();

}
