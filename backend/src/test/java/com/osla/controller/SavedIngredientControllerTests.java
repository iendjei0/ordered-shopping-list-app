package com.osla.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import java.util.Map;

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

    private List<SavedIngredient> savedIngredients;
    private List<SavedIngredient> orderedIngredients;

    @BeforeAll
    public void initMocks() {
        savedIngredients = Arrays.asList(
            SavedIngredient.builder()
                .id(1).name("egg").orderValue(3).build(),
            SavedIngredient.builder()
                .id(2).name("flour").orderValue(1).build(),
            SavedIngredient.builder()
                .id(3).name("milk").orderValue(2).build()
        );

        orderedIngredients = Arrays.asList(
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

        assertTrue(json.has("savedIngredients"));
        assertTrue(json.has("ingredientOrder"));
    }

    @Test
    public void getSavedIngredientsAndIngredientsOrder() throws Exception {
        MvcResult result = mockMvc.perform(get("/saved")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
        
        assertJsonContent(result);

        verify(savedIngredientService).getSavedIngredients();
        verify(savedIngredientService).getOrderedIngredients();
    }

    @Test
    public void addSavedIngredient() throws Exception {
        String newIngredientName = "butter";

        List<SavedIngredient> updatedSavedIngredients = Arrays.asList(
            SavedIngredient.builder().id(1).name("egg").orderValue(3).build(),
            SavedIngredient.builder().id(2).name("flour").orderValue(1).build(),
            SavedIngredient.builder().id(3).name("milk").orderValue(2).build(),
            SavedIngredient.builder().id(4).name("butter").orderValue(4).build()
        );

        List<SavedIngredient> updatedOrderedIngredients = Arrays.asList(
            SavedIngredient.builder().id(2).name("flour").orderValue(1).build(),
            SavedIngredient.builder().id(3).name("milk").orderValue(2).build(),
            SavedIngredient.builder().id(1).name("egg").orderValue(3).build(),
            SavedIngredient.builder().id(4).name("butter").orderValue(4).build()
        );

        // Mocking service responses after adding
        given(savedIngredientService.getSavedIngredients()).willReturn(updatedSavedIngredients);
        given(savedIngredientService.getOrderedIngredients()).willReturn(updatedOrderedIngredients);

        MvcResult result = mockMvc.perform(post("/saved/add/" + newIngredientName)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
        
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());

        assertTrue(json.has("savedIngredients"));
        assertTrue(json.has("ingredientOrder"));

        // Deserialize JSON arrays
        List<SavedIngredient> responseSavedIngredients = objectMapper.convertValue(
            json.get("savedIngredients"),
            objectMapper.getTypeFactory().constructCollectionType(List.class, SavedIngredient.class)
        );

        List<SavedIngredient> responseOrderedIngredients = objectMapper.convertValue(
            json.get("ingredientOrder"),
            objectMapper.getTypeFactory().constructCollectionType(List.class, SavedIngredient.class)
        );

        // Assert that "butter" is added
        assertTrue(responseSavedIngredients.stream().anyMatch(i -> i.getName().equals("butter")));
        assertTrue(responseOrderedIngredients.stream().anyMatch(i -> i.getName().equals("butter")));

        // Verify service interactions
        verify(savedIngredientService).addSavedIngredient(newIngredientName);
        verify(savedIngredientService).getSavedIngredients();
        verify(savedIngredientService).getOrderedIngredients();
    }

    @Test
    public void deleteSavedIngredient() throws Exception {
        String ingredientToDelete = "milk";

        List<SavedIngredient> updatedSavedIngredients = Arrays.asList(
            SavedIngredient.builder().id(1).name("egg").orderValue(3).build(),
            SavedIngredient.builder().id(2).name("flour").orderValue(1).build()
        );

        List<SavedIngredient> updatedOrderedIngredients = Arrays.asList(
            SavedIngredient.builder().id(2).name("flour").orderValue(1).build(),
            SavedIngredient.builder().id(1).name("egg").orderValue(3).build()
        );

        given(savedIngredientService.getSavedIngredients()).willReturn(updatedSavedIngredients);
        given(savedIngredientService.getOrderedIngredients()).willReturn(updatedOrderedIngredients);

        MvcResult result = mockMvc.perform(delete("/saved/delete/" + ingredientToDelete)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();
        
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());

        assertTrue(json.has("savedIngredients"));
        assertTrue(json.has("ingredientOrder"));

        List<SavedIngredient> responseSavedIngredients = objectMapper.convertValue(
            json.get("savedIngredients"),
            objectMapper.getTypeFactory().constructCollectionType(List.class, SavedIngredient.class)
        );

        List<SavedIngredient> responseOrderedIngredients = objectMapper.convertValue(
            json.get("ingredientOrder"),
            objectMapper.getTypeFactory().constructCollectionType(List.class, SavedIngredient.class)
        );

        assertFalse(responseSavedIngredients.stream().anyMatch(i -> i.getName().equals("milk")));
        assertFalse(responseOrderedIngredients.stream().anyMatch(i -> i.getName().equals("milk")));

        verify(ingredientManagementService).deleteIngredient(ingredientToDelete);
        verify(savedIngredientService).getSavedIngredients();
        verify(savedIngredientService).getOrderedIngredients();
    }

    @Test
    public void swapIngredientOrder() throws Exception {
        String name1 = "egg";
        String name2 = "flour";

        List<SavedIngredient> swappedOrderedIngredients = Arrays.asList(
            SavedIngredient.builder().id(1).name("egg").orderValue(1).build(),
            SavedIngredient.builder().id(2).name("flour").orderValue(2).build(),
            SavedIngredient.builder().id(3).name("milk").orderValue(3).build()
        );

        given(savedIngredientService.getSavedIngredients()).willReturn(savedIngredients); // Assuming savedIngredients remain unchanged
        given(savedIngredientService.getOrderedIngredients()).willReturn(swappedOrderedIngredients);

        String swapPayload = objectMapper.writeValueAsString(
            Map.of("name1", name1, "name2", name2)
        );

        MvcResult result = mockMvc.perform(
                put("/saved/swap")
                .contentType(MediaType.APPLICATION_JSON)
                .content(swapPayload)
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());

        assertTrue(json.has("savedIngredients"));
        assertTrue(json.has("ingredientOrder"));

        List<SavedIngredient> responseSavedIngredients = objectMapper.convertValue(
            json.get("savedIngredients"),
            objectMapper.getTypeFactory().constructCollectionType(List.class, SavedIngredient.class)
        );

        List<SavedIngredient> responseOrderedIngredients = objectMapper.convertValue(
            json.get("ingredientOrder"),
            objectMapper.getTypeFactory().constructCollectionType(List.class, SavedIngredient.class)
        );

        assertEquals(1, responseOrderedIngredients.get(0).getOrderValue());
        assertEquals("egg", responseOrderedIngredients.get(0).getName());
        assertEquals(2, responseOrderedIngredients.get(1).getOrderValue());
        assertEquals("flour", responseOrderedIngredients.get(1).getName());

        verify(savedIngredientService).swapIngredientOrder(name1, name2);
        verify(savedIngredientService).getSavedIngredients();
        verify(savedIngredientService).getOrderedIngredients();
    }
}