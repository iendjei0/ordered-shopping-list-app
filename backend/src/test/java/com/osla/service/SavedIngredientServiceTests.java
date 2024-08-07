package com.osla.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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
				.id(1).name("egg").orderValue(1).build(),
			SavedIngredient.builder()
				.id(2).name("flour").orderValue(2).build(),
			SavedIngredient.builder()
				.id(3).name("milk").orderValue(3).build()
        );

        given(savedIngredientRepository.findAll()).willReturn(savedIngredients);

        List<SavedIngredient> savedIngredients2 = savedIngredientService.getSavedIngredients();

        assertIterableEquals(savedIngredients, savedIngredients2);
    }

    @Test
    public void getSavedIngredientsIfThereAreNone() {
        given(savedIngredientRepository.findAll()).willReturn(Collections.emptyList());

        assertEquals(Collections.emptyList(), savedIngredientService.getSavedIngredients());
    }

    @Test
    public void addSavedIngredient() {
        given(savedIngredientRepository.getNextOrder()).willReturn(10);

        savedIngredientService.addSavedIngredient("milk");

        verify(savedIngredientRepository).save(
            argThat(someIngredient ->
                someIngredient.getName().equals("milk") &&
                someIngredient.getOrderValue() == 10)
        );
    }

    @Test
    public void deleteSavedIngredient() {
        given(savedIngredientRepository.findByName("milk"))
            .willReturn(SavedIngredient.builder()
                .id(13).name("milk").orderValue(4).build()
            );
        
            savedIngredientService.deleteSavedIngredient("milk");

        verify(savedIngredientRepository).deleteById(13);
        verify(savedIngredientRepository).decrementOrderHigherThan(4);
    }

    @Test
    public void swapIngredientOrder() {
        given(savedIngredientRepository.findByName("milk"))
            .willReturn(SavedIngredient.builder()
                .name("milk").orderValue(4).build()
            );
        given(savedIngredientRepository.findByName("flour"))
            .willReturn(SavedIngredient.builder()
                .name("flour").orderValue(7).build()
            );

        savedIngredientService.swapIngredientOrder("milk", "flour");

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
}
