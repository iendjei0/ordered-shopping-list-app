package com.osla;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.osla.model.CurrentIngredient;
import com.osla.repository.CurrentIngredientRepository;
import com.osla.service.CurrentIngredientService;

@SpringBootTest
public class SavedIngredientTests {

    @MockBean
	private CurrentIngredientRepository currentIngredientRepository;

	@Autowired
	public CurrentIngredientService currentIngredientService;

	@Test
	public void getCurrentIngredients() {
		CurrentIngredient ci1 = new CurrentIngredient();
		ci1.setId(1);
		ci1.setName("egg");
		ci1.setCount(3);

		CurrentIngredient ci2 = new CurrentIngredient();
		ci2.setId(2);
		ci2.setName("flour");
		ci2.setCount(2);

		CurrentIngredient ci3 = new CurrentIngredient();
		ci3.setId(3);
		ci3.setName("milk");
		ci3.setCount(5);

		List<CurrentIngredient> currentIngredients = Arrays.asList(ci1, ci2, ci3);

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
}
