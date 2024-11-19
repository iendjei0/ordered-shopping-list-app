package com.osla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.osla.model.CurrentIngredient;
import com.osla.model.OutputIngredient;
import com.osla.service.CurrentIngredientService;
import com.osla.service.IngredientManagementService;

@Controller
@RequestMapping("/current")
public class CurrentIngredientController {
    
    @Autowired
    private CurrentIngredientService currentIngredientService;

    @Autowired
    private IngredientManagementService ingredientManagementService;

    @GetMapping
    public ResponseEntity<List<CurrentIngredient>> getCurrentIngredients() {
        return ResponseEntity.ok(currentIngredientService.getCurrentIngredients());
    }

    @PostMapping("/add/{name}")
    public ResponseEntity<List<CurrentIngredient>> addCurrentIngredient(@PathVariable String name) {
        ingredientManagementService.addIngredient(name);
        return ResponseEntity.ok(currentIngredientService.getCurrentIngredients());
    }

    @PutMapping("/increment/{id}")
    public ResponseEntity<List<CurrentIngredient>> incrementCurrentIngredient(@PathVariable int id) {
        currentIngredientService.incrementCurrentIngredient(id);
        return ResponseEntity.ok(currentIngredientService.getCurrentIngredients());
    }

    @PutMapping("/decrement/{id}")
    public ResponseEntity<List<CurrentIngredient>> decrementCurrentIngredient(@PathVariable int id) {
        currentIngredientService.decrementCurrentIngredient(id);
        return ResponseEntity.ok(currentIngredientService.getCurrentIngredients());
    }

    @GetMapping("/processed")
    public ResponseEntity<List<OutputIngredient>> getSummedOrderedIngredients() {
        return ResponseEntity.ok(currentIngredientService.getSummedOrderedIngredients());
    }
    
}
