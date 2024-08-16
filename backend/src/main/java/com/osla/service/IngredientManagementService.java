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
    public void addIngredient(String name) {
        try {
            savedIngredientService.findSavedIngredient(name);
        } catch (IngredientNotFoundException e) {
            savedIngredientService.addSavedIngredient(name);
        }

        currentIngredientService.addCurrentIngredient(name);
    }

    @Transactional
    public void deleteIngredient(String name) {
        try {
            currentIngredientService.findCurrentIngredients(name);
            currentIngredientService.deleteCurrentIngredients(name);
        } catch (IngredientNotFoundException e) { }
        
        savedIngredientService.deleteSavedIngredient(name);
    }
}
