package com.osla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.osla.model.SavedIngredient;


@Repository
public interface SavedIngredientRepository extends JpaRepository<SavedIngredient, Integer> { 
    public SavedIngredient findByName(String name);

    @Query("SELECT MAX(ing.orderValue)+1 FROM SavedIngredient ing")
    public int getNextOrder();

    @Query("""
            UPDATE SavedIngredient ing
            SET ing.orderValue = ing.orderValue-1
            WHERE ing.orderValue > :givenOrder
            """)
    public void decrementOrderHigherThan(@Param("givenOrder") int givenOrder);

}