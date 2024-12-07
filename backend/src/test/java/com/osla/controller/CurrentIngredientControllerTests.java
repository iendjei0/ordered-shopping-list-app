package com.osla.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osla.model.CurrentIngredient;
import com.osla.model.OutputIngredient;
import com.osla.service.CurrentIngredientService;
import com.osla.service.IngredientManagementService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class CurrentIngredientControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CurrentIngredientService currentIngredientService;

    @MockBean
    private IngredientManagementService ingredientManagementService;

    private List<CurrentIngredient> currentIngredients;
    private String auth;

    @BeforeEach
    public void initMocks() {
        auth = "Basic " + Base64.getEncoder().encodeToString("iendjei:test".getBytes());

        currentIngredients = Arrays.asList(
            CurrentIngredient.builder()
                .id(1).name("egg").count(3).userId(1).build(),
            CurrentIngredient.builder()
                .id(2).name("flour").count(2).userId(1).build(),
            CurrentIngredient.builder()
                .id(3).name("milk").count(5).userId(1).build()
        );

        given(currentIngredientService.getCurrentIngredients(1)).willReturn(currentIngredients);
    }

    @Test
    public void getCurrentIngredientsEndpoint() throws Exception {
        MvcResult result = mockMvc.perform(get("/current")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", auth))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        String expectedJson = objectMapper.writeValueAsString(currentIngredients);
        assertEquals(expectedJson, result.getResponse().getContentAsString());

        verify(currentIngredientService).getCurrentIngredients(1);
    }

    @Test
    public void addCurrentIngredientEndpoint() throws Exception {
        MvcResult result = mockMvc.perform(post("/current/add/milk")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", auth))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        String expectedJson = objectMapper.writeValueAsString(currentIngredients);
        assertEquals(expectedJson, result.getResponse().getContentAsString());

        verify(ingredientManagementService).addIngredient("milk", 1);
    }

    @Test
    public void incrementCurrentIngredientEndpoint() throws Exception {
        MvcResult result = mockMvc.perform(put("/current/increment/17")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", auth))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        String expectedJson = objectMapper.writeValueAsString(currentIngredients);
        assertEquals(expectedJson, result.getResponse().getContentAsString());

        verify(currentIngredientService).incrementCurrentIngredient(17, 1);
    }

    @Test
    public void decrementCurrentIngredientEndpoint() throws Exception {
        MvcResult result = mockMvc.perform(put("/current/decrement/17")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", auth))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        String expectedJson = objectMapper.writeValueAsString(currentIngredients);
        assertEquals(expectedJson, result.getResponse().getContentAsString());

        verify(currentIngredientService).decrementCurrentIngredient(17, 1);
    }

    @Test
    public void getSummedOrderedIngredientsEndpoint() throws Exception {
        List<OutputIngredient> outputIngredients = Arrays.asList(
            new OutputIngredient("egg", 3),
            new OutputIngredient("flour", 2),
            new OutputIngredient("milk", 5)
        );

        given(currentIngredientService.getSummedOrderedIngredients(1)).willReturn(outputIngredients);

        MvcResult result = mockMvc.perform(get("/current/processed")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", auth))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn();

        String expectedJson = objectMapper.writeValueAsString(outputIngredients);
        assertEquals(expectedJson, result.getResponse().getContentAsString());

        verify(currentIngredientService).getSummedOrderedIngredients(1);
    }
}