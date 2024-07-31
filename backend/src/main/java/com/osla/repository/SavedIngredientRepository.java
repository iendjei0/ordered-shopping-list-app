package com.osla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.osla.model.SavedIngredient;

@Repository
public interface SavedIngredientRepository extends JpaRepository<SavedIngredient, Integer> {   
}
