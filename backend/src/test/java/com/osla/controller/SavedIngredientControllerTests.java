package com.osla.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osla.model.SavedIngredient;
import com.osla.service.IngredientManagementService;
import com.osla.service.SavedIngredientService;

@WebMvcTest(SavedIngredientController.class)
@TestInstance(Lifecycle.PER_CLASS)
public class SavedIngredientControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SavedIngredientService savedIngredientService;

    @MockBean
    private IngredientManagementService ingredientManagementService;

    @BeforeAll
    public void initMocks() {
        List<SavedIngredient> savedIngredients = Arrays.asList(
            SavedIngredient.builder()
				.id(1).name("egg").orderValue(3).build(),
			SavedIngredient.builder()
				.id(2).name("flour").orderValue(1).build(),
			SavedIngredient.builder()
				.id(3).name("milk").orderValue(2).build()
        );

        List<SavedIngredient> orderedIngredients = Arrays.asList(
			SavedIngredient.builder()
				.id(2).name("flour").orderValue(1).build(),
			SavedIngredient.builder()
				.id(3).name("milk").orderValue(2).build(),
            SavedIngredient.builder()
				.id(1).name("egg").orderValue(3).build()
        );

        given(savedIngredientService.getSavedIngredients()).willReturn(savedIngredients);
        given(savedIngredientService.getOrderedIngredients()).willReturn(orderedIngredients);
    }

    private void assertJsonContent(MvcResult result) throws Exception {
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());

        assertTrue(json.has("saved-ingredients"));
        assertTrue(json.has("ingredient-order"));
    }

    @Test
    public void getSavedIngredientsAndIngredientsOrder() throws Exception {
        MvcResult result = mockMvc.perform(get("/saved"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andReturn();
        
        assertJsonContent(result);

        verify(savedIngredientService).getSavedIngredients();
    }

    @Test
    public void addSavedIngredient() throws Exception {
        MvcResult result = mockMvc.perform(post("/saved/add/butter"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andReturn();
        
        assertJsonContent(result);

        verify(savedIngredientService).addSavedIngredient("butter");
    }

    @Test
    public void deleteSavedIngredient() throws Exception {
        MvcResult result = mockMvc.perform(delete("/saved/delete/milk"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andReturn();
        
        assertJsonContent(result);

        verify(ingredientManagementService).deleteIngredient("milk");
    }

    @Test
    public void swapIngredientOrder() throws Exception {
        MvcResult result = mockMvc.perform(
                put("/saved/swap")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name1\":\"egg\", \"name2\":\"flour\"}")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/html;charset=UTF-8"))
            .andReturn();

        verify(savedIngredientService).getOrderedIngredients();
    }

}
