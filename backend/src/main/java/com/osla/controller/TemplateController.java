package com.osla.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.osla.repository.CurrentIngredientRepository;



@Controller
public class TemplateController {

    @Autowired
    private CurrentIngredientRepository ingredientRepository;

    @GetMapping("/")
    public String getHomePage() {
        return "index";
    }

    @GetMapping("/settings")
    public String getSettingsPage() {
        return "b-side";
    }
    
}
