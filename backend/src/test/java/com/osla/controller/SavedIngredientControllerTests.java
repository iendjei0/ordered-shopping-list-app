package com.osla.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osla.model.SavedIngredient;
import com.osla.service.IngredientManagementService;
import com.osla.service.SavedIngredientService;

@SpringBootTest
@AutoConfigureMockMvc
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
    private String auth;

    @BeforeAll
    public void initMocks() {
        auth = "Basic " + Base64.getEncoder().encodeToString("iendjei:test".getBytes());

        savedIngredients = Arrays.asList(
            SavedIngredient.builder()
                .id(1).name("egg").orderValue(3).userId(1).build(),
            SavedIngredient.builder()
                .id(2).name("flour").orderValue(1).userId(1).build(),
            SavedIngredient.builder()
                .id(3).name("milk").orderValue(2).userId(1).build()
        );

        orderedIngredients = Arrays.asList(
            SavedIngredient.builder()
                .id(2).name("flour").orderValue(1).userId(1).build(),
            SavedIngredient.builder()
                .id(3).name("milk").orderValue(2).userId(1).build(),
            SavedIngredient.builder()
                .id(1).name("egg").orderValue(3).userId(1).build()
        );

        given(savedIngredientService.getSavedIngredients(anyInt())).willReturn(savedIngredients);
        given(savedIngredientService.getOrderedIngredients(anyInt())).willReturn(orderedIngredients);
    }

    @Test
    public void addSavedIngredient() throws Exception {
        String newIngredientName = "butter";

        MvcResult result = mockMvc.perform(post("/api/saved/add/" + newIngredientName)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", auth))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        verify(savedIngredientService).addSavedIngredient(eq(newIngredientName), anyInt());

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        assertTrue(json.has("savedIngredients"));
        assertTrue(json.has("ingredientOrder"));
    }

    @Test
    public void deleteSavedIngredient() throws Exception {
        String ingredientToDelete = "milk";

        MvcResult result = mockMvc.perform(delete("/api/saved/delete/" + ingredientToDelete)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", auth))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        verify(ingredientManagementService).deleteIngredient(eq(ingredientToDelete), anyInt());

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        assertTrue(json.has("savedIngredients"));
        assertTrue(json.has("ingredientOrder"));
    }

    @Test
    public void swapIngredientOrder() throws Exception {
        String name1 = "egg";
        String name2 = "flour";

        String swapPayload = objectMapper.writeValueAsString(
            Map.of("name1", name1, "name2", name2)
        );

        MvcResult result = mockMvc.perform(
                put("/api/saved/swap")
                .contentType(MediaType.APPLICATION_JSON)
                .content(swapPayload)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", auth)
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        verify(savedIngredientService).swapIngredientOrder(eq(name1), eq(name2), anyInt());

        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        assertTrue(json.has("savedIngredients"));
        assertTrue(json.has("ingredientOrder"));
    }
}