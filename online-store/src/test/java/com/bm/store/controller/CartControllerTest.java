package com.bm.store.controller;

import com.bm.store.dto.representation.AddProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartControllerTest {
    private static ObjectMapper objectMapper;
    private final String expectedDefaultCustomerCart = """
            {"totalTaxes":0.0,
             "totalPrice":0.0,
             "cartItems":[],
             "_links":{"self":{"href":"http://localhost/api/cart/TESTUID"}}}""";


    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void setUp(){
        objectMapper = new ObjectMapper();
    }

    //TODO Use different users and add new test cases


    @Test
    @Order(1)
    void readCart_whenCartIsEmpty_shouldReturnsDefaultCustomerCart() throws Exception {
        this.mockMvc.perform(get("/api/cart/TESTUID"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedDefaultCustomerCart));
    }

    @Test
    @Order(2)
    void addProductToCart_whenProductIsNotFound_shouldReturnCartWithOneProduct() throws Exception {
        AddProductDTO addProductDTO = AddProductDTO.builder()
                .quantity(1).productId(-1).build();
        this.mockMvc.perform(patch("/api/cart/TESTUID")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(addProductDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"message\":\"Could not find the product -1\"}"));
    }

    @ParameterizedTest(name = "When quantity is {0} then should return validation error message")
    @ValueSource(longs = {-1L, 0L})
    @Order(3)
    void addProductToCart_whenQuantityNotPositive_shouldReturnBadRequest(long quantity) throws Exception {
        AddProductDTO addProductDTO = AddProductDTO.builder()
                .quantity(quantity).productId(1).build();
        String expectedError = """
                {"message":"Invalid value for the field :quantity must be greater than 0. "}
                """;

        this.mockMvc.perform(patch("/api/cart/TESTUID")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(addProductDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(expectedError));
    }

    @Test
    @Order(4)
    void addProductToCart_whenCartIsEmpty_shouldReturnCartWithOneProduct() throws Exception {
        AddProductDTO addProductDTO = AddProductDTO.builder()
                .quantity(1).productId(1).build();
        String expectedCartWithOneProduct = """
                {
                "totalTaxes":1.3,
                "totalPrice":11.3,
                "cartItems":[{
                  "product" : {
                    "productCode" : "KM45",
                    "name" : "RDS",
                    "productType" : "Channel",
                    "description" : "Sport channel in french",
                    "imageUrl" : "https://rdsimages.cookieless.ca/polopoly_fs/1.6843299.1562016209!/img/httpImage/image.jpg",
                    "price" : 10.00,
                    "taxable" : true
                  },
                  "quantity" : 1
                }],
                "_links":{
                    "self":{
                    "href":"http://localhost/api/cart/TESTUID"
                   },
                    "products":{
                      "href":"http://localhost/api/product/1",
                      "title": "KM45",
                      "type": "Channel",
                      "name": "RDS"
                    }
                  }
                }""";

        this.mockMvc.perform(patch("/api/cart/TESTUID")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(addProductDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedCartWithOneProduct));
    }

    @Test
    @Order(4)
    void removeProductToCart_whenProductIsNotFound_shouldReturn404() throws Exception {
        this.mockMvc.perform(delete("/api/cart/TESTUID/product/-1?qt=1"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"message\":\"Could not find the product -1\"}"));
    }

    @Test
    @Order(5)
    void removeProductToCart_whenCartDoesNotContainProduct_shouldReturnUnModifiedCart() throws Exception {
        this.mockMvc.perform(delete("/api/cart/TESTUID/product/3?qt=1"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"message\":\"Could not find the product 3 in the cart.\"}"));
    }

    @Test
    @Order(6)
    void removeProductToCart_whenCartIsNotEmpty_shouldReturnEmptyCart() throws Exception {
        this.mockMvc.perform(delete("/api/cart/TESTUID/product/1?qt=1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedDefaultCustomerCart));
    }

}
