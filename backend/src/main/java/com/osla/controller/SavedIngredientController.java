package com.osla.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.osla.model.SavedIngredient;
import com.osla.service.IngredientManagementService;
import com.osla.service.SavedIngredientService;

@RestController
@RequestMapping("/saved")
public class SavedIngredientController {

    @Autowired
    private SavedIngredientService savedIngredientService;

    @Autowired
    private IngredientManagementService ingredientManagementService;

    @GetMapping
    public ResponseEntity<Map<String, List<SavedIngredient>>> getSavedIngredientsAndIngredientOrder() {
        List<SavedIngredient> savedIngredients = savedIngredientService.getSavedIngredients();
        List<SavedIngredient> orderedIngredients = savedIngredientService.getOrderedIngredients();

        return ResponseEntity.ok(Map.of(
            "savedIngredients", savedIngredients,
            "ingredientOrder", orderedIngredients
        ));
    }

    @PostMapping("/add/{name}")
    public ResponseEntity<Map<String, List<SavedIngredient>>> addSavedIngredient(@PathVariable String name) {
        savedIngredientService.addSavedIngredient(name);
        return getSavedIngredientsAndIngredientOrder();
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Map<String, List<SavedIngredient>>> deleteSavedIngredient(@PathVariable String name) {
        ingredientManagementService.deleteIngredient(name);
        return getSavedIngredientsAndIngredientOrder();
    }

    @PutMapping("/swap")
    public ResponseEntity<Map<String, List<SavedIngredient>>> swapIngredientsOrder(@RequestBody JsonNode names) {
        String name1 = names.get("name1").asText();
        String name2 = names.get("name2").asText();
        savedIngredientService.swapIngredientOrder(name1, name2);
        return getSavedIngredientsAndIngredientOrder();
    }
}