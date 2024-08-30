package com.osla.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.fasterxml.jackson.databind.JsonNode;
import com.osla.model.SavedIngredient;
import com.osla.service.IngredientManagementService;
import com.osla.service.SavedIngredientService;

@Controller
@RequestMapping("/saved")
public class SavedIngredientController {
    @Autowired
    private SavedIngredientService savedIngredientService;

    @Autowired
    private IngredientManagementService ingredientManagementService;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    private Map<String, String> prepareSavedIngredientsAndIngredientsOrder(Model model) {
        List<SavedIngredient> ingredients = savedIngredientService.getSavedIngredients();
        List<SavedIngredient> orderedIngredients = savedIngredientService.getOrderedIngredients();
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("ordered", orderedIngredients);
        Context context = new Context();
        context.setVariables(model.asMap());

        return Map.of(
            "saved-ingredients", springTemplateEngine.process("fragments", Set.of("saved-ingredients"), context),
            "ingredient-order", springTemplateEngine.process("fragments", Set.of("ingredient-order"), context)
        );
    }

    @GetMapping()
    @ResponseBody
    public Map<String, String> getSavedIngredientsAndIngredientsOrder(Model model) {
        return prepareSavedIngredientsAndIngredientsOrder(model);
    }

    @PostMapping("/add/{name}")
    @ResponseBody
    public Map<String, String> addSavedIngredient(@PathVariable String name, Model model) {
        savedIngredientService.addSavedIngredient(name);
        return prepareSavedIngredientsAndIngredientsOrder(model);
    }

    @DeleteMapping("/delete/{name}")
    @ResponseBody
    public Map<String, String> deleteSavedIngredient(@PathVariable String name, Model model) {
        ingredientManagementService.deleteIngredient(name);
        return prepareSavedIngredientsAndIngredientsOrder(model);
    }

    @PutMapping("/swap")
    public String swapIngredientsOrder(@RequestBody JsonNode names, Model model) {
        String name1 = names.get("name1").asText();
        String name2 = names.get("name2").asText();
        savedIngredientService.swapIngredientOrder(name1, name2);

        model.addAttribute("ordered", savedIngredientService.getOrderedIngredients());
        return "fragments :: ingredient-order";
    }
}
