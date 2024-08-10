package com.osla.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.osla.model.CurrentIngredient;
import com.osla.service.CurrentIngredientService;
import com.osla.service.IngredientManagementService;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class CurrentIngredientControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrentIngredientService currentIngredientService;

    @MockBean
    private IngredientManagementService ingredientManagementService;

    @BeforeAll
    public void initMocks() {
        List<CurrentIngredient> currentIngredients = Arrays.asList(
			CurrentIngredient.builder()
				.id(1).name("egg").count(3).build(),
			CurrentIngredient.builder()
				.id(2).name("flour").count(2).build(),
			CurrentIngredient.builder()
				.id(3).name("milk").count(5).build()
		);

        given(currentIngredientService.getCurrentIngredients()).willReturn(currentIngredients);
    }

    private void testHTMLOutput(MvcResult result) throws UnsupportedEncodingException {
        String returnedHtml = result.getResponse().getContentAsString();

        Document doc = Jsoup.parse(returnedHtml);
        
        assertEquals(3, doc.select("div").size());
        assertEquals("egg", doc.select("#ingredient-1 span").first().text());
        assertEquals("2", doc.select("#ingredient-2 span").get(1).text());
    }

    @Test
    public void getCurrentIngredientsEndpoint() throws Exception {
        MvcResult result = mockMvc.perform(get("/current"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/html;charset=UTF-8"))
            .andReturn();

        testHTMLOutput(result);

        verify(currentIngredientService).getCurrentIngredients();
    }

    @Test
    public void addCurrentIngredientEndpoint() throws Exception {
        MvcResult result = mockMvc.perform(post("/current/add/milk"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/html;charset=UTF-8"))
            .andReturn();

        testHTMLOutput(result);

        verify(ingredientManagementService).addIngredient("milk");
    }

    @Test
    public void incrementCurrentIngredientEndpoint() throws Exception {
        MvcResult result = mockMvc.perform(put("/current/increment/milk"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/html;charset=UTF-8"))
            .andReturn();

        testHTMLOutput(result);

        verify(currentIngredientService).incrementCurrentIngredient("milk");
    }

    @Test
    public void decrementCurrentIngredientEndpoint() throws Exception {
        MvcResult result = mockMvc.perform(put("/current/decrement/milk"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/html;charset=UTF-8"))
            .andReturn();

        testHTMLOutput(result);

        verify(currentIngredientService).decrementCurrentIngredient("milk");
    }
}
