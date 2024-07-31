package com.osla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.osla.model.CurrentIngredient;
import com.osla.service.CurrentIngredientService;



@Controller
@RequestMapping("/current")
public class CurrentIngredientController {
    
    @Autowired
    private CurrentIngredientService ingredientService;

    @GetMapping()
    public String getCurrentIngredients(Model model) {
        List<CurrentIngredient> ingredients = ingredientService.getCurrentIngredients();
        model.addAttribute("ingredients", ingredients);
        return "fragments :: ingredients-list";
    }
    
    //addCurrentIngredient
    //deleteCurrentIngredient

    //Maybe:
    //incrementIngredientCount
    //decrementIngredientCount

    //ordered endpoint
    //getOrderedIngredients
    
}
