package com.osla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.osla.model.SavedIngredient;

import jakarta.transaction.Transactional;


@Repository
public interface SavedIngredientRepository extends JpaRepository<SavedIngredient, Integer> { 
    public List<SavedIngredient> findByUserId(int userId);
    public SavedIngredient findByNameAndUserId(String name, int userId);

    @Transactional
    @Query("""
            SELECT COALESCE(MAX(ing.orderValue)+1, 1) 
            FROM SavedIngredient ing
            WHERE ing.userId = :userId
            """)
    public int getNextOrder(@Param("userId") int userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("""
            UPDATE SavedIngredient ing
            SET ing.orderValue = ing.orderValue-1
            WHERE ing.orderValue > :givenOrder
            """)
    public void decrementOrderHigherThan(@Param("givenOrder") int givenOrder);

    @Query("""
            SELECT ing
            FROM SavedIngredient ing
            WHERE ing.userId = :userId
            ORDER BY ing.orderValue
            """)
    public List<SavedIngredient> getOrderedIngredients(@Param("userId") int userId);
}