package com.osla.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.osla.model.SavedIngredient;

@DataJpaTest
public class SavedIngredientRepositoryTests {

    @Autowired
    private SavedIngredientRepository savedIngredientRepository;

    @Test
    public void findByName() {
        SavedIngredient expected = SavedIngredient.builder()
            .name("milk").orderValue(3).userId(1).build();

        savedIngredientRepository.save(expected);

        SavedIngredient returned = savedIngredientRepository.findByNameAndUserId("milk", 1);

        assertEquals(expected, returned);
    }

    @Test
    public void findByNameNotExist() {
        savedIngredientRepository.save(SavedIngredient.builder()
            .name("notmilk").orderValue(3).userId(1).build());

        SavedIngredient returned = savedIngredientRepository.findByNameAndUserId("milk", 1);
        
        assertNull(returned);
    }

    @Test
    public void getNextOrder() {
        savedIngredientRepository.saveAll(Arrays.asList(
            SavedIngredient.builder()
                .name("milk").orderValue(1).userId(1).build(),
            SavedIngredient.builder()
                .name("flour").orderValue(2).userId(1).build(),
            SavedIngredient.builder()
                .name("butter").orderValue(3).userId(1).build()
        ));
        
        assertEquals(4, savedIngredientRepository.getNextOrder(1));
    }

    @Test
    public void getNextOrderEmptyDatabase() {
        assertEquals(1, savedIngredientRepository.getNextOrder(1));
    }

    @Test
    public void createRecordsWithGetNextOrder() {
        int orderValue = savedIngredientRepository.getNextOrder(1);
        savedIngredientRepository.save(SavedIngredient.builder()
            .name("milk").orderValue(orderValue).userId(1).build());
        
        orderValue = savedIngredientRepository.getNextOrder(1);
        savedIngredientRepository.save(SavedIngredient.builder()
            .name("flour").orderValue(orderValue).userId(1).build());

        orderValue = savedIngredientRepository.getNextOrder(1);
        savedIngredientRepository.save(SavedIngredient.builder()
            .name("butter").orderValue(orderValue).userId(1).build());

        List<SavedIngredient> savedIngredients = savedIngredientRepository.findByUserId(1);
        for(int i = 0; i < savedIngredients.size(); i++) {
            assertEquals(i+1, savedIngredients.get(i).getOrderValue());
        }
    }

    @Test
    public void decrementOrderHigherThan() {
        List<SavedIngredient> savedIngredients = Arrays.asList(
            SavedIngredient.builder()
                .name("milk").orderValue(1).userId(1).build(),
            SavedIngredient.builder()
                .name("flour").orderValue(2).userId(1).build(),
            SavedIngredient.builder()
                .name("butter").orderValue(3).userId(1).build(),
            SavedIngredient.builder()
                .name("egg").orderValue(4).userId(1).build()
        );
        savedIngredientRepository.saveAll(savedIngredients);

        savedIngredientRepository.decrementOrderHigherThan(2);

        List<SavedIngredient> updatedIngredients = savedIngredientRepository.findByUserId(1);
        for(int i = 0; i < 2; i++) {
            assertEquals(savedIngredients.get(i).getOrderValue(), updatedIngredients.get(i).getOrderValue());
        }
        for(int i = 2; i < updatedIngredients.size(); i++) {
            assertEquals(savedIngredients.get(i).getOrderValue(), updatedIngredients.get(i).getOrderValue()+1);
        }
    }

    @Test
    public void decrementOrderHigherThan0() {
        List<SavedIngredient> savedIngredients = Arrays.asList(
            SavedIngredient.builder()
                .name("milk").orderValue(1).userId(1).build(),
            SavedIngredient.builder()
                .name("flour").orderValue(2).userId(1).build(),
            SavedIngredient.builder()
                .name("butter").orderValue(3).userId(1).build(),
            SavedIngredient.builder()
                .name("egg").orderValue(4).userId(1).build()
        );
        savedIngredientRepository.saveAll(savedIngredients);

        savedIngredientRepository.decrementOrderHigherThan(0);

        List<SavedIngredient> updatedIngredients = savedIngredientRepository.findByUserId(1);
        for(int i = 0; i < updatedIngredients.size(); i++) {
            assertEquals(savedIngredients.get(i).getOrderValue(), updatedIngredients.get(i).getOrderValue()+1);
        }
    }

    @Test
    public void getOrderedIngredients() {
        List<SavedIngredient> savedIngredients = Arrays.asList(
            SavedIngredient.builder()
                .name("milk").orderValue(3).userId(1).build(),
            SavedIngredient.builder()
                .name("flour").orderValue(1).userId(1).build(),
            SavedIngredient.builder()
                .name("butter").orderValue(4).userId(1).build(),
            SavedIngredient.builder()
                .name("egg").orderValue(2).userId(1).build()
        );
        savedIngredientRepository.saveAll(savedIngredients);

        List<SavedIngredient> orderedIngredients = savedIngredientRepository.getOrderedIngredients(1);

        String[] namesOrdered = {"flour", "egg", "milk", "butter"};

        for(int i = 0; i < orderedIngredients.size(); i++) {
            assertEquals(namesOrdered[i], orderedIngredients.get(i).getName());
        }
    }
}
