package com.osla.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exceptions.IngredientNotFoundException;
import com.osla.model.CurrentIngredient;
import com.osla.model.OutputIngredient;
import com.osla.repository.CurrentIngredientRepository;

import jakarta.transaction.Transactional;

@Service
public class CurrentIngredientService {
    @Autowired
    private CurrentIngredientRepository currentIngredientRepository;

    public List<CurrentIngredient> getCurrentIngredients() {
        return currentIngredientRepository.findAll();
    }

    //TODO: somehow find by id not name because of duplicates
    public CurrentIngredient findCurrentIngredient(String name) {
        CurrentIngredient ingredient = currentIngredientRepository.findByName(name);
        if(ingredient == null) throw new IngredientNotFoundException("Couldn't find ingredient \"" + name + "\"");
        return ingredient;
    }

    @Transactional
    public void addCurrentIngredient(String name) {
        currentIngredientRepository.save(
            CurrentIngredient.builder()
                .name(name)
                .count(1).build());
    }

    @Transactional
    public void deleteCurrentIngredient(String name) {
        CurrentIngredient ingredient = findCurrentIngredient(name);
        currentIngredientRepository.delete(ingredient);
    }

    @Transactional
    public void incrementCurrentIngredient(String name) {
        CurrentIngredient ingredient = findCurrentIngredient(name);
        int count = ingredient.getCount();
        if(count == Integer.MAX_VALUE) return;
        ingredient.setCount(++count);

        currentIngredientRepository.save(ingredient);
    }
    
    @Transactional
    public void decrementCurrentIngredient(String name) {
        CurrentIngredient ingredient = findCurrentIngredient(name);
        int count = ingredient.getCount();
        ingredient.setCount(--count);

        if(count > 0) {
            currentIngredientRepository.save(ingredient);
        } else {
            currentIngredientRepository.deleteById(ingredient.getId());
        }
    }

    public List<OutputIngredient> getSummedOrderedIngredients() {
        return currentIngredientRepository.getSummedOrderedIngredients();
    }
}
