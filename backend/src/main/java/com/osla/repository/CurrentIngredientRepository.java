package com.osla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.osla.model.CurrentIngredient;
import com.osla.model.OutputIngredient;


@Repository
public interface CurrentIngredientRepository extends JpaRepository<CurrentIngredient, Integer> {
    public List<CurrentIngredient> findByNameAndUserId(String name, int userId);
    public CurrentIngredient findByIdAndUserId(int id, int userId);
    public List<CurrentIngredient> findAllByUserId(int userId);

    @Query("""
            SELECT new com.osla.model.OutputIngredient(ci.name, SUM(ci.count))
            FROM CurrentIngredient ci
            JOIN SavedIngredient si ON ci.name=si.name
            WHERE ci.userId = :userId
            GROUP BY ci.name, si.orderValue
            ORDER BY si.orderValue
            """)
    public List<OutputIngredient> getSummedOrderedIngredients(@Param("userId") int userId);
}
