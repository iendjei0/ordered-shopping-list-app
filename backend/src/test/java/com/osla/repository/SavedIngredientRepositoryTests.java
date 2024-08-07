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
            .name("milk").orderValue(3).build();

        savedIngredientRepository.save(expected);

        SavedIngredient returned = savedIngredientRepository.findByName("milk");

        assertEquals(expected, returned);
    }

    @Test
    public void findByNameNotExist() {
        savedIngredientRepository.save(SavedIngredient.builder()
            .name("notmilk").orderValue(3).build());

        SavedIngredient returned = savedIngredientRepository.findByName("milk");
        
        assertNull(returned);
    }

    @Test
    public void getNextOrder() {
        savedIngredientRepository.saveAll(Arrays.asList(
            SavedIngredient.builder()
                .name("milk").orderValue(1).build(),
            SavedIngredient.builder()
                .name("flour").orderValue(2).build(),
            SavedIngredient.builder()
                .name("butter").orderValue(3).build()
        ));
        
        assertEquals(4, savedIngredientRepository.getNextOrder());
    }

    @Test
    public void getNextOrderEmptyDatabase() {
        assertEquals(1, savedIngredientRepository.getNextOrder());
    }

    @Test
    public void createRecordsWithGetNextOrder() {
        int orderValue = savedIngredientRepository.getNextOrder();
        savedIngredientRepository.save(SavedIngredient.builder()
            .name("milk").orderValue(orderValue).build());
        
        orderValue = savedIngredientRepository.getNextOrder();
        savedIngredientRepository.save(SavedIngredient.builder()
            .name("flour").orderValue(orderValue).build());

        orderValue = savedIngredientRepository.getNextOrder();
        savedIngredientRepository.save(SavedIngredient.builder()
            .name("butter").orderValue(orderValue).build());

        List<SavedIngredient> savedIngredients = savedIngredientRepository.findAll();
        for(int i = 0; i < savedIngredients.size(); i++) {
            assertEquals(i+1, savedIngredients.get(i).getOrderValue());
        }
    }

    @Test
    public void decrementOrderHigherThan() {
        List<SavedIngredient> savedIngredients = Arrays.asList(
            SavedIngredient.builder()
                .name("milk").orderValue(1).build(),
            SavedIngredient.builder()
                .name("flour").orderValue(2).build(),
            SavedIngredient.builder()
                .name("butter").orderValue(3).build(),
            SavedIngredient.builder()
                .name("egg").orderValue(4).build()
        );
        savedIngredientRepository.saveAll(savedIngredients);

        savedIngredientRepository.decrementOrderHigherThan(2);
        
        List<SavedIngredient> updatedIngredients = savedIngredientRepository.findAll();
        //Unchanged
        for(int i = 0; i < 2; i++) {
            assertEquals(savedIngredients.get(i).getOrderValue(), updatedIngredients.get(i).getOrderValue());
        }
        //Decremented
        for(int i = 2; i < updatedIngredients.size(); i++) {
            assertEquals(savedIngredients.get(i).getOrderValue(), updatedIngredients.get(i).getOrderValue()+1);
        }
    }

    @Test
    public void decrementOrderHigherThan0() {
        List<SavedIngredient> savedIngredients = Arrays.asList(
            SavedIngredient.builder()
                .name("milk").orderValue(1).build(),
            SavedIngredient.builder()
                .name("flour").orderValue(2).build(),
            SavedIngredient.builder()
                .name("butter").orderValue(3).build(),
            SavedIngredient.builder()
                .name("egg").orderValue(4).build()
        );
        savedIngredientRepository.saveAll(savedIngredients);

        savedIngredientRepository.decrementOrderHigherThan(0);
        
        List<SavedIngredient> updatedIngredients = savedIngredientRepository.findAll();
        for(int i = 0; i < updatedIngredients.size(); i++) {
            assertEquals(savedIngredients.get(i).getOrderValue(), updatedIngredients.get(i).getOrderValue()+1);
        }
    }

    

}
