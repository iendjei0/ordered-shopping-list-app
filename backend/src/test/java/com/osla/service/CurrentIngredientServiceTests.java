package com.osla.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.exceptions.IngredientNotFoundException;
import com.osla.model.CurrentIngredient;
import com.osla.repository.CurrentIngredientRepository;

@SpringBootTest
public class CurrentIngredientServiceTests {

    @MockBean
    private CurrentIngredientRepository currentIngredientRepository;

    @Autowired
    private CurrentIngredientService currentIngredientService;

    @Test
    public void getCurrentIngredients() {
        List<CurrentIngredient> currentIngredients = Arrays.asList(
            CurrentIngredient.builder()
                .id(1).name("egg").count(3).userId(1).build(),
            CurrentIngredient.builder()
                .id(2).name("flour").count(2).userId(1).build(),
            CurrentIngredient.builder()
                .id(3).name("milk").count(5).userId(1).build()
        );

        given(currentIngredientRepository.findAllByUserId(1)).willReturn(currentIngredients);

        List<CurrentIngredient> currentIngredients2 = currentIngredientService.getCurrentIngredients(1);

        assertTrue(currentIngredientsAreEqual(currentIngredients, currentIngredients2));
    }

    private boolean currentIngredientsAreEqual(List<CurrentIngredient> list1, List<CurrentIngredient> list2) {
        if (list1.size() != list2.size()) return false;
        for (int i = 0; i < list1.size(); i++) {
            CurrentIngredient a = list1.get(i);
            CurrentIngredient b = list2.get(i);
            if (!a.toString().equals(b.toString())) return false;
        }
        return true;
    }

    @Test
    public void getCurrentIngredientsIfThereAreNone() {
        given(currentIngredientRepository.findAllByUserId(1)).willReturn(Collections.emptyList());

        assertEquals(Collections.emptyList(), currentIngredientService.getCurrentIngredients(1));
    }

    @Test
    public void findCurrentIngredient() {
        given(currentIngredientRepository.findByNameAndUserId("milk", 1)).willReturn(mock(List.class));

        currentIngredientService.findCurrentIngredients("milk", 1);

        verify(currentIngredientRepository).findByNameAndUserId("milk", 1);
    }

    @Test
    public void findCurrentIngredientNotExisting() {
        given(currentIngredientRepository.findByNameAndUserId("milk", 1)).willThrow(IngredientNotFoundException.class);

        assertThrows(IngredientNotFoundException.class, () -> {
            currentIngredientService.findCurrentIngredients("milk", 1);
        });

        verify(currentIngredientRepository).findByNameAndUserId("milk", 1);
    }

    @Test
    public void addCurrentIngredient() {
        currentIngredientService.addCurrentIngredient("milk", 1);

        verify(currentIngredientRepository).save(
            argThat(someIngredient -> 
                someIngredient.getName().equals("milk") &&
                someIngredient.getCount() == 1 &&
                someIngredient.getUserId() == 1)
        );
    }

    @Test
    public void deleteCurrentIngredient() {
        currentIngredientService.deleteCurrentIngredient(17, 1);

        verify(currentIngredientRepository).deleteById(17);
    }

    @Test
    public void incrementCurrentIngredient() {
        given(currentIngredientRepository.findByIdAndUserId(17, 1))
            .willReturn(CurrentIngredient.builder().id(17).name("milk").count(1).userId(1).build());

        int previousCount = currentIngredientRepository.findByIdAndUserId(17, 1).getCount();

        currentIngredientService.incrementCurrentIngredient(17, 1);

        verify(currentIngredientRepository).save(
            argThat(someIngredient -> someIngredient.getCount() == previousCount + 1)
        );
    }

    @Test
    public void decrementCurrentIngredient() {
        given(currentIngredientRepository.findByIdAndUserId(17, 1))
            .willReturn(CurrentIngredient.builder().id(17).name("milk").count(3).userId(1).build());

        int previousCount = currentIngredientRepository.findByIdAndUserId(17, 1).getCount();

        currentIngredientService.decrementCurrentIngredient(17, 1);

        verify(currentIngredientRepository).save(
            argThat(someIngredient -> someIngredient.getCount() == previousCount - 1)
        );
    }

    @Test
    public void getSummedOrderedIngredients() {
        currentIngredientService.getSummedOrderedIngredients(1);

        verify(currentIngredientRepository).getSummedOrderedIngredients(1);
    }
}