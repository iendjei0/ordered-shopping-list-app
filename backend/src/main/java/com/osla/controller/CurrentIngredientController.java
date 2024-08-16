package com.osla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.osla.service.CurrentIngredientService;
import com.osla.service.IngredientManagementService;

@Controller
@RequestMapping("/current")
public class CurrentIngredientController {
    
    @Autowired
    private CurrentIngredientService currentIngredientService;

    @Autowired
    private IngredientManagementService ingredientManagementService;

    private String prepareCurrentIngredientsModel(Model model) {
        model.addAttribute("ingredients", currentIngredientService.getCurrentIngredients());
        return "fragments :: ingredients-list";
    }

    @GetMapping()
    public String getCurrentIngredients(Model model) {
        return prepareCurrentIngredientsModel(model);
    }
    
    @PostMapping("/add/{name}")
    public String addCurrentIngredient(@PathVariable String name, Model model) {
        ingredientManagementService.addIngredient(name);
        return prepareCurrentIngredientsModel(model);
    }

    @PutMapping("/increment/{id}")
    public String incrementCurrentIngredient(@PathVariable int id, Model model) {
        currentIngredientService.incrementCurrentIngredient(id);
        return prepareCurrentIngredientsModel(model);
    }
    
    @PutMapping("/decrement/{id}")
    public String decrementCurrentIngredient(@PathVariable int id, Model model) {
        currentIngredientService.decrementCurrentIngredient(id);
        return prepareCurrentIngredientsModel(model);
    }
    
    @GetMapping("/processed")
    public String getSummedOrderedIngredients(Model model) {
        model.addAttribute("ingredients", currentIngredientService.getSummedOrderedIngredients());
        return "fragments :: shopping-list";
    }
    
}
