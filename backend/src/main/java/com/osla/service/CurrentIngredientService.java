package com.osla.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osla.model.CurrentIngredient;
import com.osla.repository.CurrentIngredientRepository;

@Service
public class CurrentIngredientService {
    @Autowired
    private CurrentIngredientRepository ingredientRepository;

    public List<CurrentIngredient> getCurrentIngredients() {
        return ingredientRepository.findAll();
    }

    //addCurrentIngredient
    //deleteCurrentIngredient

    //Maybe:
    //incrementIngredientCount
    //decrementIngredientCount

    //ordered endpoint
    //getOrderedIngredients
}
