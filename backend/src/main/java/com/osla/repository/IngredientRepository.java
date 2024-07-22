package com.osla.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.osla.model.Ingredient;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {
    public Ingredient findIngredientById(int id);
    public void deleteByName(String name);
}
