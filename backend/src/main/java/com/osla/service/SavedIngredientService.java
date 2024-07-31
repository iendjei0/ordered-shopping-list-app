package com.osla.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osla.repository.SavedIngredientRepository;

@Service
public class SavedIngredientService {
    @Autowired
    private SavedIngredientRepository savedIngredientRepository;

    //getSavedIngredients
    //addSavedIngredient
    //deleteSavedIngredient
    //swapIngredientsOrder
}
