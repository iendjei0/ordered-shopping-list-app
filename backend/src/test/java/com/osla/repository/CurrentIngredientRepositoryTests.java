package com.osla.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.osla.model.CurrentIngredient;
import com.osla.model.OutputIngredient;
import com.osla.model.SavedIngredient;

@DataJpaTest()
public class CurrentIngredientRepositoryTests {

    @Autowired
    private CurrentIngredientRepository currentIngredientRepository;

    @Autowired
    private SavedIngredientRepository savedIngredientRepository;

    @Test
    public void findByName() {
        List<CurrentIngredient> expected = Arrays.asList(CurrentIngredient.builder()
            .name("milk").count(3).userId(1).build());

        currentIngredientRepository.save(expected.get(0));

        List<CurrentIngredient> returned = currentIngredientRepository.findByNameAndUserId("milk", 1);

        assertEquals(expected, returned);
    }

    @Test
    public void findByNameNotExist() {
        currentIngredientRepository.save(CurrentIngredient.builder()
            .name("notmilk").count(3).userId(1).build());

        List<CurrentIngredient> returned = currentIngredientRepository.findByNameAndUserId("milk", 1);
        
        assertEquals(Collections.emptyList(), returned);
    }

    @Test
    public void getSummedOrderedIngredients() {
        List<CurrentIngredient> currentIngredients = Arrays.asList(
            CurrentIngredient.builder()
                .name("egg").count(3).userId(1).build(),
            CurrentIngredient.builder()
                .name("flour").count(1).userId(1).build(),
            CurrentIngredient.builder()
                .name("butter").count(3).userId(1).build(),
            CurrentIngredient.builder()
                .name("milk").count(2).userId(1).build(),
            CurrentIngredient.builder()
                .name("egg").count(7).userId(1).build()
        );

        List<SavedIngredient> savedIngredients = Arrays.asList(
            SavedIngredient.builder()
                .name("milk").orderValue(1).userId(1).build(),
            SavedIngredient.builder()
                .name("flour").orderValue(2).userId(1).build(),
            SavedIngredient.builder()
                .name("yoghurt").orderValue(3).userId(1).build(),
            SavedIngredient.builder()
                .name("butter").orderValue(4).userId(1).build(),
            SavedIngredient.builder()
                .name("egg").orderValue(5).userId(1).build()
        );

        currentIngredientRepository.saveAll(currentIngredients);
        savedIngredientRepository.saveAll(savedIngredients);

        List<OutputIngredient> returned = currentIngredientRepository.getSummedOrderedIngredients(1);

        List<OutputIngredient> expected = Arrays.asList(
            OutputIngredient.builder()
                .name("milk").count(2).build(),
            OutputIngredient.builder()
                .name("flour").count(1).build(),
            OutputIngredient.builder()
                .name("butter").count(3).build(),
            OutputIngredient.builder()
                .name("egg").count(10).build()
        );

        assertIterableEquals(expected, returned);
    }

    @Test
    public void getSummedOrderedIngredientsIfThereAreNone() {
        assertEquals(Collections.emptyList(), currentIngredientRepository.getSummedOrderedIngredients(1));
    }
}
