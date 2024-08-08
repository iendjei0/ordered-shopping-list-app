package com.osla.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.osla.model.CurrentIngredient;
import com.osla.repository.CurrentIngredientRepository;

@SpringBootTest
public class CurrentIngredientServiceTests {

    @MockBean
	private CurrentIngredientRepository currentIngredientRepository;

	@Autowired
	public CurrentIngredientService currentIngredientService;

	@Test
	public void getCurrentIngredients() {
		List<CurrentIngredient> currentIngredients = Arrays.asList(
			CurrentIngredient.builder()
				.id(1).name("egg").count(3).build(),
			CurrentIngredient.builder()
				.id(2).name("flour").count(2).build(),
			CurrentIngredient.builder()
				.id(3).name("milk").count(5).build()
		);

		given(currentIngredientRepository.findAll()).willReturn(currentIngredients);

		List<CurrentIngredient> currentIngredients2 = currentIngredientService.getCurrentIngredients();

		assertTrue(currentIngredientsAreEqual(currentIngredients, currentIngredients2));
	}

	private boolean currentIngredientsAreEqual(List<CurrentIngredient> list1, List<CurrentIngredient> list2) {
		if(list1.size() != list2.size()) return false;
		for(int i = 0; i < list1.size(); i++) {
			CurrentIngredient a = list1.get(i);
			CurrentIngredient b = list2.get(i);
			if(!a.toString().equals(b.toString())) return false;
		}
		return true;
	}

	@Test
	public void getCurrentIngredientsIfThereAreNone() {
		given(currentIngredientRepository.findAll()).willReturn(Collections.emptyList());

		assertEquals(Collections.emptyList(), currentIngredientService.getCurrentIngredients());
	}

	@Test
	public void addCurrentIngredient() {
		currentIngredientService.addCurrentIngredient("milk");

		verify(currentIngredientRepository).save(
			argThat(someIngredient -> 
				someIngredient.getName().equals("milk") &&
				someIngredient.getCount() == 1)
		);
	}

	@Test
	public void deleteCurrentIngredient() {
		given(currentIngredientRepository.findByName("milk"))
			.willReturn(CurrentIngredient.builder().name("milk").build());

		currentIngredientService.deleteCurrentIngredient("milk");

		verify(currentIngredientRepository).delete(
			argThat(someIngredient -> someIngredient.getName().equals("milk"))
		);
	}

	@Test
	public void incrementCurrentIngredient() {
		given(currentIngredientRepository.findByName("milk"))
			.willReturn(CurrentIngredient.builder().name("milk").count(1).build());

		int previousCount = currentIngredientRepository.findByName("milk").getCount();

		currentIngredientService.incrementCurrentIngredient("milk");

		verify(currentIngredientRepository).save(
			argThat(someIngredient -> someIngredient.getCount() == previousCount+1)
		);
	}

	@Test
	public void decrementCurrentIngredient() {
		given(currentIngredientRepository.findByName("milk"))
			.willReturn(CurrentIngredient.builder().name("milk").count(3).build());

		int previousCount = currentIngredientRepository.findByName("milk").getCount();

		currentIngredientService.decrementCurrentIngredient("milk");

		verify(currentIngredientRepository).save(
			argThat(someIngredient -> someIngredient.getCount() == previousCount-1)
		);
	}

	@Test
	public void getSummedOrderedIngredients() {
		currentIngredientService.getSummedOrderedIngredients();

		verify(currentIngredientRepository).getSummedOrderedIngredients();
	}
}
