package com.osla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.osla.model.CurrentIngredient;


@Repository
public interface CurrentIngredientRepository extends JpaRepository<CurrentIngredient, Integer> {
    public CurrentIngredient findByName(String name);
}
