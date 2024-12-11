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
        String expectedCatalogue = """
                {
                  "id" : 6, 
                  "region" : "Short", 
                  "startDate" : "2024-07-01", 
                  "endDate" : "2024-12-31", 
                  "catalogProducts" : [ { 
                  "id" : 8, 
                  "productCode" : "BPM45", 
                  "name" : "Game of thrones", 
                  "productType" : "TV show", 
                  "description" : null, 
                  "imageUrl" : null, 
                  "price" : 4.99, 
                  "taxable" : true, 
                  "_links" : { 
                    "self" : {
                      "href" : "http://localhost/api/product/8"
                    }
                  } 
                }, {
                  "id" : 7, 
                  "productCode" : "KM77", 
                  "name" : "HBO", 
                  "productType" : "Channel", 
                  "description" : "Movies and TV shows channel", 
                  "imageUrl" : "https://asset.entpay.9c9media.ca/image-service/version/c:YTNjOWRjMTMtZDQzNi00:NDk3NDA2/image.png", 
                  "price" : 5.00, 
                  "taxable" : true, 
                  "_links" : {
                    "self" : {
                      "href" : "http://localhost/api/product/7"
                    }
                  }
                } ], 
                "_links" : { 
                  "self" : {
                    "href" : "http://localhost/api/catalogue/6" 
                  }
                } 
                }""";
        this.mockMvc.perform(get("/api/catalogue/6"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedCatalogue));
    }
}
