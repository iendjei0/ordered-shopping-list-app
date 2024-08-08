package com.osla.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osla.model.CurrentIngredient;
import com.osla.model.OutputIngredient;
import com.osla.repository.CurrentIngredientRepository;

@Service
public class CurrentIngredientService {
    @Autowired
    private CurrentIngredientRepository currentIngredientRepository;

    public List<CurrentIngredient> getCurrentIngredients() {
        return currentIngredientRepository.findAll();
    }

    public void addCurrentIngredient(String name) {
        currentIngredientRepository.save(
            CurrentIngredient.builder()
                .name(name)
                .count(1).build());
    }

    public void deleteCurrentIngredient(String name) {
        CurrentIngredient ingredient = currentIngredientRepository.findByName(name);
        currentIngredientRepository.delete(ingredient);
    }

    public void incrementCurrentIngredient(String name) {
        CurrentIngredient ingredient = currentIngredientRepository.findByName(name);
        int count = ingredient.getCount();
        ingredient.setCount(++count);

        currentIngredientRepository.save(ingredient);
    }
    
    public void decrementCurrentIngredient(String name) {
        CurrentIngredient ingredient = currentIngredientRepository.findByName(name);
        int count = ingredient.getCount();
        ingredient.setCount(--count);

        currentIngredientRepository.save(ingredient);
    }

    public List<OutputIngredient> getSummedOrderedIngredients() {
        return currentIngredientRepository.getSummedOrderedIngredients();
    }
}
