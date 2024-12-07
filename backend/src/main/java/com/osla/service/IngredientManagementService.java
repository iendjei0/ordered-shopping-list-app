package com.osla.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exceptions.IngredientNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class IngredientManagementService {
    
    @Autowired
    private CurrentIngredientService currentIngredientService;

    @Autowired
    private SavedIngredientService savedIngredientService;
    
    @Transactional
    public void addIngredient(String name, int userId) {
        try {
            savedIngredientService.findSavedIngredient(name, userId);
        } catch (IngredientNotFoundException e) {
            savedIngredientService.addSavedIngredient(name, userId);
        }

        currentIngredientService.addCurrentIngredient(name, userId);
    }

    @Transactional
    public void deleteIngredient(String name, int userId) {
        try {
            currentIngredientService.findCurrentIngredients(name, userId);
            currentIngredientService.deleteCurrentIngredients(name, userId);
        } catch (IngredientNotFoundException e) { }
        
        savedIngredientService.deleteSavedIngredient(name, userId);
    }
}
