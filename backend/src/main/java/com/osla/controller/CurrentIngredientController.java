package com.osla.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.osla.model.CurrentIngredient;
import com.osla.model.OutputIngredient;
import com.osla.security.UserDetailsCustom;
import com.osla.service.CurrentIngredientService;
import com.osla.service.IngredientManagementService;

@Controller
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/current")
public class CurrentIngredientController {
    
    @Autowired
    private CurrentIngredientService currentIngredientService;

    @Autowired
    private IngredientManagementService ingredientManagementService;

    private int getUserId() {
       UserDetailsCustom userDetails = (UserDetailsCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       return userDetails.getId();
    }

    @GetMapping
    public ResponseEntity<List<CurrentIngredient>> getCurrentIngredients() {
        return ResponseEntity.ok(currentIngredientService.getCurrentIngredients(getUserId()));
    }

    @PostMapping("/add/{name}")
    public ResponseEntity<List<CurrentIngredient>> addCurrentIngredient(@PathVariable String name) {
        int userId = getUserId();
        ingredientManagementService.addIngredient(name, userId);
        return ResponseEntity.ok(currentIngredientService.getCurrentIngredients(userId));
    }

    @PutMapping("/increment/{id}")
    public ResponseEntity<List<CurrentIngredient>> incrementCurrentIngredient(@PathVariable int id) {
        int userId = getUserId();
        currentIngredientService.incrementCurrentIngredient(id, userId);
        return ResponseEntity.ok(currentIngredientService.getCurrentIngredients(userId));
    }

    @PutMapping("/decrement/{id}")
    public ResponseEntity<List<CurrentIngredient>> decrementCurrentIngredient(@PathVariable int id) {
        int userId = getUserId();
        currentIngredientService.decrementCurrentIngredient(id, userId);
        return ResponseEntity.ok(currentIngredientService.getCurrentIngredients(userId));
    }

    @GetMapping("/processed")
    public ResponseEntity<List<OutputIngredient>> getSummedOrderedIngredients() {
        return ResponseEntity.ok(currentIngredientService.getSummedOrderedIngredients(getUserId()));
    }
    
}
