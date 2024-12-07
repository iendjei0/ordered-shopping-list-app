package com.osla.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.exceptions.IngredientNotFoundException;
import com.osla.model.CurrentIngredient;
import com.osla.model.SavedIngredient;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Base64;
import java.util.List;

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
        given(savedIngredientService.findSavedIngredient("milk", 1)).willThrow(IngredientNotFoundException.class);

        ingredientManagementService.addIngredient("milk", 1);

        verify(savedIngredientService).addSavedIngredient("milk", 1);
        verify(currentIngredientService).addCurrentIngredient("milk", 1);
    }

    @Test
    public void addExistingIngredient() {
        given(savedIngredientService.findSavedIngredient("milk", 1)).willReturn(mock(SavedIngredient.class));

        ingredientManagementService.addIngredient("milk", 1);

        verify(savedIngredientService, never()).addSavedIngredient("milk", 1);
        verify(currentIngredientService).addCurrentIngredient("milk", 1);
    }

    @Test
    public void deleteIngredient() {
        given(currentIngredientService.findCurrentIngredients("milk", 1)).willThrow(IngredientNotFoundException.class);

        ingredientManagementService.deleteIngredient("milk", 1);

        verify(currentIngredientService, never()).deleteCurrentIngredients("milk", 1);
        verify(savedIngredientService).deleteSavedIngredient("milk", 1);
    }

    @Test
    public void deleteIngredientWhichIsOnCurrentList() {
        given(currentIngredientService.findCurrentIngredients("milk", 1)).willReturn(mock(List.class));

        ingredientManagementService.deleteIngredient("milk", 1);

        verify(currentIngredientService).deleteCurrentIngredients("milk", 1);
        verify(savedIngredientService).deleteSavedIngredient("milk", 1);
    }
}