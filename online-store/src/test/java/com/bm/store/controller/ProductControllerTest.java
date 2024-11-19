package com.bm.store.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getProduct_whenProductIdExists_thenShouldReturnProduct() throws Exception {
        String expectedProduct = "{\"id\":1,\"productCode\":\"KM45\",\"name\":\"RDS\",\"productType\":\"Channel\"," +
                "\"description\":\"Sport channel in french\"," +
                "\"imageUrl\":\"https://rdsimages.cookieless.ca/polopoly_fs/1.6843299.1562016209!/img/httpImage/image.jpg\"," +
                "\"price\":10.00,\"taxable\":true,\"_links\":{\"self\":{\"href\":\"http://localhost/api/product/1\"}}}";

        this.mockMvc.perform(get("/api/product/1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(expectedProduct));
    }

    @Test
    public void getProduct_whenProductIdIsNotFound_thenShouldReturn404() throws Exception {
        this.mockMvc.perform(get("/api/product/-1"))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().string("Could not find the product -1"));
    }
}
