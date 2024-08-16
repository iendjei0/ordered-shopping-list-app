package com.osla.service;

import java.util.List;
import java.util.Optional;

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

    public List<CurrentIngredient> findCurrentIngredients(String name) {
        List<CurrentIngredient> ingredients = currentIngredientRepository.findByName(name);
        if(ingredients.isEmpty()) throw new IngredientNotFoundException("Couldn't find ingredient \"" + name + "\"");
        return ingredients;
    }

    public CurrentIngredient findCurrentIngredient(int id) {
        Optional<CurrentIngredient> ingredient = currentIngredientRepository.findById(id);
        if(!ingredient.isPresent()) throw new IngredientNotFoundException("Couldn't find ingredient \"" + id + "\"");
        return ingredient.get();
    } 

    @Transactional
    public void addCurrentIngredient(String name) {
        currentIngredientRepository.save(
            CurrentIngredient.builder()
                .name(name)
                .count(1).build());
    }

    @Transactional
    public void deleteCurrentIngredients(String name) {
        currentIngredientRepository.deleteAll(findCurrentIngredients(name));
    }

    @Transactional
    public void deleteCurrentIngredient(int id) {
        currentIngredientRepository.deleteById(id);
    }

    @Transactional
    public void incrementCurrentIngredient(int id) {
        CurrentIngredient ingredient = findCurrentIngredient(id);
        int count = ingredient.getCount();
        if(count == Integer.MAX_VALUE) return;
        ingredient.setCount(++count);

        currentIngredientRepository.save(ingredient);
    }
    
    @Transactional
    public void decrementCurrentIngredient(int id) {
        CurrentIngredient ingredient = findCurrentIngredient(id);
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
