package com.osla.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osla.model.SavedIngredient;
import com.osla.repository.SavedIngredientRepository;

import jakarta.transaction.Transactional;

@Service
public class SavedIngredientService {

    @Autowired
    private SavedIngredientRepository savedIngredientRepository;

    public List<SavedIngredient> getSavedIngredients() {
        return savedIngredientRepository.findAll();
    }

    public void addSavedIngredient(String name) {
        savedIngredientRepository.save(SavedIngredient.builder()
            .name(name)
            .orderValue(savedIngredientRepository.getNextOrder())
            .build()
        );
    }

    @Transactional
    public void deleteSavedIngredient(String name) {
        SavedIngredient ingredientToDelete = savedIngredientRepository.findByName(name);

        savedIngredientRepository.deleteById(ingredientToDelete.getId());
        savedIngredientRepository.decrementOrderHigherThan(ingredientToDelete.getOrderValue());
    }
    
    @Transactional
    public void swapIngredientOrder(String name1, String name2) {
        SavedIngredient ingredient1 = savedIngredientRepository.findByName(name1);
        SavedIngredient ingredient2 = savedIngredientRepository.findByName(name2);

        int order1 = ingredient1.getOrderValue();
        int order2 = ingredient2.getOrderValue();

        ingredient1.setOrderValue(order2);
        ingredient2.setOrderValue(order1);
        
        savedIngredientRepository.save(ingredient1);
        savedIngredientRepository.save(ingredient2);
    }
}
