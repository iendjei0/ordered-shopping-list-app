package com.osla.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class IngredientManagementService {
    
    @Autowired
    private CurrentIngredientService currentIngredientService;

    @Autowired
    private SavedIngredientService savedIngredientService;
    
    @Transactional
    public void addIngredient(String name) {
        if(savedIngredientService.findSavedIngredient(name) == null) {
            savedIngredientService.addSavedIngredient(name);
        }
        currentIngredientService.addCurrentIngredient(name);
    }

    @Transactional
    public void deleteIngredient(String name) {
        if(currentIngredientService.findCurrentIngredient(name) != null) {
            currentIngredientService.deleteCurrentIngredient(name);
        }
        savedIngredientService.deleteSavedIngredient(name);
    }
}
