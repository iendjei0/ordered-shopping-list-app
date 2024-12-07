package com.osla.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exceptions.IngredientNotFoundException;
import com.osla.model.SavedIngredient;
import com.osla.repository.SavedIngredientRepository;

import jakarta.transaction.Transactional;

@Service
public class SavedIngredientService {

    @Autowired
    private SavedIngredientRepository savedIngredientRepository;

    public List<SavedIngredient> getSavedIngredients(int userId) {
        return savedIngredientRepository.findByUserId(userId);
    }

    public SavedIngredient findSavedIngredient(String name, int userId) {
        SavedIngredient ingredient = savedIngredientRepository.findByNameAndUserId(name, userId);
        if(ingredient == null) throw new IngredientNotFoundException("Couldn't find ingredient \"" + name + "\"");
        return ingredient;
    }

    @Transactional
    public void addSavedIngredient(String name, int userId) {
        savedIngredientRepository.save(SavedIngredient.builder()
            .name(name)
            .orderValue(savedIngredientRepository.getNextOrder(userId))
            .userId(userId)
            .build()
        );
    }

    @Transactional
    public void deleteSavedIngredient(String name, int userId) {
        SavedIngredient ingredientToDelete = findSavedIngredient(name, userId);

        savedIngredientRepository.deleteById(ingredientToDelete.getId());
        savedIngredientRepository.decrementOrderHigherThan(ingredientToDelete.getOrderValue());
    }
    
    @Transactional
    public void swapIngredientOrder(String name1, String name2, int userId) {
        SavedIngredient ingredient1 = findSavedIngredient(name1, userId);
        SavedIngredient ingredient2 = findSavedIngredient(name2, userId);

        int order1 = ingredient1.getOrderValue();
        int order2 = ingredient2.getOrderValue();

        ingredient1.setOrderValue(order2);
        ingredient2.setOrderValue(order1);
        
        savedIngredientRepository.save(ingredient1);
        savedIngredientRepository.save(ingredient2);
    }

    public List<SavedIngredient> getOrderedIngredients(int userId) {
        return savedIngredientRepository.getOrderedIngredients(userId);
    }
}
