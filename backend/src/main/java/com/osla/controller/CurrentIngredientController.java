package com.osla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.osla.model.CurrentIngredient;
import com.osla.model.OutputIngredient;
import com.osla.service.CurrentIngredientService;
import com.osla.service.IngredientManagementService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;





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

    @PutMapping("/increment/{name}")
    public String incrementCurrentIngredient(@PathVariable String name, Model model) {
        currentIngredientService.incrementCurrentIngredient(name);
        return prepareCurrentIngredientsModel(model);
    }
    
    @PutMapping("/decrement/{name}")
    public String decrementCurrentIngredient(@PathVariable String name, Model model) {
        currentIngredientService.decrementCurrentIngredient(name);
        return prepareCurrentIngredientsModel(model);
    }
    
    @GetMapping("/processed")
    public String getSummedOrderedIngredients(Model model) {
        model.addAttribute("ingredients", currentIngredientService.getSummedOrderedIngredients());
        return "fragments :: shopping-list";
    }
    
}
