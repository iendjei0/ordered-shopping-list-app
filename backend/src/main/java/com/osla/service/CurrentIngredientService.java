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

    public List<CurrentIngredient> getCurrentIngredients(int userId) {
        return currentIngredientRepository.findAllByUserId(userId);
    }

    public List<CurrentIngredient> findCurrentIngredients(String name, int userId) {
        List<CurrentIngredient> ingredients = currentIngredientRepository.findByNameAndUserId(name, userId);
        if(ingredients.isEmpty()) throw new IngredientNotFoundException("Couldn't find ingredient \"" + name + "\"");
        return ingredients;
    }

    public CurrentIngredient findCurrentIngredient(int id, int userId) {
        CurrentIngredient ingredient = currentIngredientRepository.findByIdAndUserId(id, userId);
        if(ingredient == null) throw new IngredientNotFoundException("Couldn't find ingredient \"" + id + "\"");
        return ingredient;
    } 

    @Transactional
    public void addCurrentIngredient(String name, int userId) {
        currentIngredientRepository.save(
            CurrentIngredient.builder()
                .name(name)
                .count(1)
                .userId(userId).build());
    }

    @Transactional
    public void deleteCurrentIngredients(String name, int userId) {
        currentIngredientRepository.deleteAll(findCurrentIngredients(name, userId));
    }

    @Transactional
    public void deleteCurrentIngredient(int id, int userId) {
        currentIngredientRepository.deleteById(id);
    }

    @Transactional
    public void incrementCurrentIngredient(int id, int userId) {
        CurrentIngredient ingredient = findCurrentIngredient(id, userId);
        int count = ingredient.getCount();
        if(count == Integer.MAX_VALUE) return;
        ingredient.setCount(++count);

        currentIngredientRepository.save(ingredient);
    }
    
    @Transactional
    public void decrementCurrentIngredient(int id, int userId) {
        CurrentIngredient ingredient = findCurrentIngredient(id, userId);
        int count = ingredient.getCount();
        ingredient.setCount(--count);

        if(count > 0) {
            currentIngredientRepository.save(ingredient);
        } else {
            currentIngredientRepository.deleteById(ingredient.getId());
        }
    }
    
    public List<OutputIngredient> getSummedOrderedIngredients(int userId) {
        return currentIngredientRepository.getSummedOrderedIngredients(userId);
    }
}
