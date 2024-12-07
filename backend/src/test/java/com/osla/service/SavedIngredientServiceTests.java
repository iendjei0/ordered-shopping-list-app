package com.osla.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.exceptions.IngredientNotFoundException;
import com.osla.model.SavedIngredient;
import com.osla.repository.SavedIngredientRepository;

@SpringBootTest
public class SavedIngredientServiceTests {

    @MockBean
    private SavedIngredientRepository savedIngredientRepository;

    @Autowired
    private SavedIngredientService savedIngredientService;

    @Test
    public void getSavedIngredients() {
        List<SavedIngredient> savedIngredients = Arrays.asList(
            SavedIngredient.builder()
                .id(1).name("egg").orderValue(1).userId(1).build(),
            SavedIngredient.builder()
                .id(2).name("flour").orderValue(2).userId(1).build(),
            SavedIngredient.builder()
                .id(3).name("milk").orderValue(3).userId(1).build()
        );

        given(savedIngredientRepository.findByUserId(1)).willReturn(savedIngredients);

        List<SavedIngredient> savedIngredients2 = savedIngredientService.getSavedIngredients(1);

        assertIterableEquals(savedIngredients, savedIngredients2);
    }

    @Test
    public void getSavedIngredientsIfThereAreNone() {
        given(savedIngredientRepository.findByUserId(1)).willReturn(Collections.emptyList());

        assertEquals(Collections.emptyList(), savedIngredientService.getSavedIngredients(1));
    }

    @Test
    public void findSavedIngredient() {
        given(savedIngredientRepository.findByNameAndUserId("milk", 1)).willReturn(mock(SavedIngredient.class));

        savedIngredientService.findSavedIngredient("milk", 1);

        verify(savedIngredientRepository).findByNameAndUserId("milk", 1);
    }

    @Test
    public void findSavedIngredientNotExisting() {
        given(savedIngredientRepository.findByNameAndUserId("milk", 1)).willThrow(IngredientNotFoundException.class);

        assertThrows(IngredientNotFoundException.class, () -> {
            savedIngredientService.findSavedIngredient("milk", 1);
        });

        verify(savedIngredientRepository).findByNameAndUserId("milk", 1);
    }

    @Test
    public void addSavedIngredient() {
        given(savedIngredientRepository.getNextOrder(1)).willReturn(10);

        savedIngredientService.addSavedIngredient("milk", 1);

        verify(savedIngredientRepository).save(
            argThat(someIngredient ->
                someIngredient.getName().equals("milk") &&
                someIngredient.getOrderValue() == 10 &&
                someIngredient.getUserId() == 1)
        );
    }

    @Test
    public void deleteSavedIngredient() {
        given(savedIngredientRepository.findByNameAndUserId("milk", 1))
            .willReturn(SavedIngredient.builder()
                .id(13).name("milk").orderValue(4).userId(1).build()
            );
        
        savedIngredientService.deleteSavedIngredient("milk", 1);

        verify(savedIngredientRepository).deleteById(13);
        verify(savedIngredientRepository).decrementOrderHigherThan(4);
    }

    @Test
    public void swapIngredientOrder() {
        given(savedIngredientRepository.findByNameAndUserId("milk", 1))
            .willReturn(SavedIngredient.builder()
                .name("milk").orderValue(4).userId(1).build()
            );
        given(savedIngredientRepository.findByNameAndUserId("flour", 1))
            .willReturn(SavedIngredient.builder()
                .name("flour").orderValue(7).userId(1).build()
            );

        savedIngredientService.swapIngredientOrder("milk", "flour", 1);

        verify(savedIngredientRepository).save(
            argThat(someIngredient ->
                someIngredient.getName().equals("milk") &&
                someIngredient.getOrderValue() == 7)
        );
        verify(savedIngredientRepository).save(
            argThat(someIngredient ->
                someIngredient.getName().equals("flour") &&
                someIngredient.getOrderValue() == 4)
        );
    }

    @Test
    public void getOrderedIngredients() {
        savedIngredientService.getOrderedIngredients(1);

        verify(savedIngredientRepository).getOrderedIngredients(1);
    }
}