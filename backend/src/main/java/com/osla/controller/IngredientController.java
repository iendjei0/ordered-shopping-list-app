package com.osla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.osla.model.Ingredient;
import com.osla.repository.IngredientRepository;

import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    
    @Autowired
    private IngredientRepository ingredientRepository;
    
    @GetMapping()
    public String test() {
        return "test";
    }

    @GetMapping("/{id}")
    public Ingredient getNameById(@PathVariable int id) {
        return ingredientRepository.findIngredientById(id);
    }

    @PostMapping()
    public ResponseEntity<String> postIngredient(@RequestBody Ingredient ingredient) {
        ingredientRepository.save(ingredient);
        return new ResponseEntity<String>("Added", HttpStatus.valueOf(201));
    }

    @DeleteMapping()
    @Transactional
    public ResponseEntity<String> deleteIngredient(@RequestBody Ingredient ingredient) {
        ingredientRepository.deleteByName(ingredient.getName());
        return new ResponseEntity<>("Deleted", HttpStatus.valueOf(200));
    }

    
}
