package com.osla.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.osla.model.CurrentIngredient;
import com.osla.service.CurrentIngredientService;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrentIngredientControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrentIngredientService currentIngredientService;

    @Test
    public void getCurrentIngredientsEndpoint() throws Exception {
        List<CurrentIngredient> currentIngredients = Arrays.asList(
			CurrentIngredient.builder()
				.id(1).name("egg").count(3).build(),
			CurrentIngredient.builder()
				.id(2).name("flour").count(2).build(),
			CurrentIngredient.builder()
				.id(3).name("milk").count(5).build()
		);

        given(currentIngredientService.getCurrentIngredients()).willReturn(currentIngredients);

        MvcResult result = mockMvc.perform(get("/current"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/html;charset=UTF-8"))
            .andReturn();

        String returnedHtml = result.getResponse().getContentAsString();

        Document doc = Jsoup.parse(returnedHtml);
        
        assertEquals(3, doc.select("div").size());
        assertEquals("egg", doc.select("#ingredient-1 span").first().text());
        assertEquals("2", doc.select("#ingredient-2 span").get(1).text());
    }

}
