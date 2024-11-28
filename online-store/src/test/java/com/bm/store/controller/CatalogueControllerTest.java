package com.bm.store.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CatalogueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void readCatalogue_whenCatalogueIdIsNotFound_thenShouldReturn404() throws Exception {
        this.mockMvc.perform(get("/api/catalogue/-1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Could not find the catalogue -1"));
    }

    @Test
    void readCatalogue_whenCatalogueIdExists_thenShouldReturn200() throws Exception {
        String expectedCatalogue = "{\n" +
                "  \"id\" : 6,\n" +
                "  \"region\" : \"Short\",\n" +
                "  \"startDate\" : \"2024-07-01\",\n" +
                "  \"endDate\" : \"2024-12-31\",\n" +
                "  \"catalogProducts\" : [ {\n" +
                "    \"id\" : 8,\n" +
                "    \"productCode\" : \"BPM45\",\n" +
                "    \"name\" : \"Game of thrones\",\n" +
                "    \"productType\" : \"TV show\",\n" +
                "    \"description\" : null,\n" +
                "    \"imageUrl\" : null,\n" +
                "    \"price\" : 4.99,\n" +
                "    \"taxable\" : true,\n" +
                "    \"_links\" : {\n" +
                "      \"self\" : {\n" +
                "        \"href\" : \"http://localhost/api/product/8\"\n" +
                "      }\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"id\" : 7,\n" +
                "    \"productCode\" : \"KM77\",\n" +
                "    \"name\" : \"HBO\",\n" +
                "    \"productType\" : \"Channel\",\n" +
                "    \"description\" : \"Movies and TV shows channel\",\n" +
                "    \"imageUrl\" : \"https://asset.entpay.9c9media.ca/image-service/version/c:YTNjOWRjMTMtZDQzNi00:NDk3NDA2/image.png\",\n" +
                "    \"price\" : 5.00,\n" +
                "    \"taxable\" : true,\n" +
                "    \"_links\" : {\n" +
                "      \"self\" : {\n" +
                "        \"href\" : \"http://localhost/api/product/7\"\n" +
                "      }\n" +
                "    }\n" +
                "  } ],\n" +
                "  \"_links\" : {\n" +
                "    \"self\" : {\n" +
                "      \"href\" : \"http://localhost/api/catalogue/6\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        this.mockMvc.perform(get("/api/catalogue/6"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedCatalogue));
    }
}
