package com.osla.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.osla.model.SavedIngredient;


@Repository
public interface SavedIngredientRepository extends JpaRepository<SavedIngredient, Integer> { 
    public SavedIngredient findByName(String name);

    @Query("SELECT MAX(orderValue)+1 FROM SAVED_INGREDIENTS")
    public int getNextOrder();

    @Query("""
            UPDATE SAVED_INGREDIENTS
            SET orderValue = orderValue-1
            WHERE orderValue > givenOrder;
            """)
    public void decrementOrderHigherThan(int givenOrder);

}
