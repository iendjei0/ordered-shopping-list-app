package com.osla.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.osla.model.CurrentIngredient;
import com.osla.model.SavedIngredient;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class IngredientManagementServiceTests {
    
    @MockBean
    private CurrentIngredientService currentIngredientService;

    @MockBean
    private SavedIngredientService savedIngredientService;

    @Autowired
    private IngredientManagementService ingredientManagementService;

    @Test
    public void addNewIngredient() {
        given(savedIngredientService.findSavedIngredient("milk")).willReturn(null);

        ingredientManagementService.addIngredient("milk");

        verify(savedIngredientService).addSavedIngredient("milk");
        verify(currentIngredientService).addCurrentIngredient("milk");
    }

    @Test
    public void addExistingIngredient() {
        given(savedIngredientService.findSavedIngredient("milk")).willReturn(mock(SavedIngredient.class));

        ingredientManagementService.addIngredient("milk");

        verify(savedIngredientService, never()).addSavedIngredient("milk");
        verify(currentIngredientService).addCurrentIngredient("milk");
    }

    @Test
    public void deleteIngredient() {
        given(currentIngredientService.findCurrentIngredient("milk")).willReturn(null);

        ingredientManagementService.deleteIngredient("milk");

        verify(currentIngredientService, never()).deleteCurrentIngredient("milk");
        verify(savedIngredientService).deleteSavedIngredient("milk");
    }

    @Test
    public void deleteIngredientWhichIsOnCurrentList() {
        given(currentIngredientService.findCurrentIngredient("milk")).willReturn(mock(CurrentIngredient.class));

        ingredientManagementService.deleteIngredient("milk");

        verify(currentIngredientService).deleteCurrentIngredient("milk");
        verify(savedIngredientService).deleteSavedIngredient("milk");
    }
}
