package com.osla.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.osla.model.CurrentIngredient;

@DataJpaTest
public class CurrentIngredientRepositoryTests {

    @Autowired
    private CurrentIngredientRepository currentIngredientRepository;

    @Test
    public void findByName() {
        CurrentIngredient expected = CurrentIngredient.builder()
            .name("milk").count(3).build();

        currentIngredientRepository.save(expected);

        CurrentIngredient returned = currentIngredientRepository.findByName("milk");

        assertEquals(expected, returned);
    }

    @Test
    public void findByNameNotExist() {
        currentIngredientRepository.save(CurrentIngredient.builder()
            .name("notmilk").count(3).build());

        CurrentIngredient returned = currentIngredientRepository.findByName("milk");
        
        assertNull(returned);
    }

}
