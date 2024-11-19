package com.bm.store.controller;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CartControllerTest {

    private final String expectedDefaultCustomerCart = "{\"totalTaxes\":0.0," +
            "\"totalPrice\":0.0,\"cartItems\":[]," +
            "\"_links\":{\"self\":{\"href\":\"http://localhost/api/cart\"}}}";

    private final String expectedCartWithOneProduct = "{\"selectedProducts\":" +
            "{\"Product(id=1, productCode=KM45, name=RDS, productType=Channel, description=Sport channel in french, " +
            "imageUrl=https://rdsimages.cookieless.ca/polopoly_fs/1.6843299.1562016209!/img/httpImage/image.jpg, " +
            "price=10.00, isTaxable=true)\":1}," +
            "\"totalTaxes\":1.3,\"totalPrice\":11.3," +
            "\"cartItems\":[{\"id\":1,\"productCode\":\"KM45\",\"name\":\"RDS\",\"productType\":\"Channel\"," +
            "\"description\":\"Sport channel in french\"," +
            "\"imageUrl\":\"https://rdsimages.cookieless.ca/polopoly_fs/1.6843299.1562016209!/img/httpImage/image.jpg\"," +
            "\"price\":10.00,\"quantity\":1,\"taxable\":true}],\"_links\":{\"self\":{\"href\":\"http://localhost/api/cart\"}," +
            "\"products\":{\"href\":\"http://localhost/api/product/1\"}}}";


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test1_readCart_whenCartIsEmpty_shouldReturnsDefaultCustomerCart() throws Exception {
        this.mockMvc.perform(get("/api/cart"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedDefaultCustomerCart));
    }

    @Test
    public void test2_addProductToCart_whenProductIsNotFound_shouldReturnCartWithOneProduct() throws Exception {
        this.mockMvc.perform(patch("/api/cart/product/-1?qt=1")).andExpect(status().isNotFound())
                .andExpect(content().string("Could not find the product -1"));
    }

    @Test
    public void test3_addProductToCart_whenCartIsEmpty_shouldReturnCartWithOneProduct() throws Exception {
        this.mockMvc.perform(patch("/api/cart/product/1?qt=1")).andExpect(status().isOk())
                .andExpect(content().json(expectedCartWithOneProduct));
    }

    @Test
    public void test4_removeProductToCart_whenProductIsNotFound_shouldReturn404() throws Exception {
        this.mockMvc.perform(delete("/api/cart/product/-1?qt=1")).andExpect(status().isNotFound())
                .andExpect(content().string("Could not find the product -1"));
    }

    @Test
    public void test4_removeProductToCart_whenCartDoesNotContainProduct_shouldReturnUnModifiedCart() throws Exception {
        this.mockMvc.perform(delete("/api/cart/product/3?qt=1")).andExpect(status().isNotFound())
                .andExpect(content().string("Could not find the product 3 in the cart."));
    }

    @Test
    public void test5_removeProductToCart_whenCartIsNotEmpty_shouldReturnEmptyCart() throws Exception {
        this.mockMvc.perform(delete("/api/cart/product/1?qt=1")).andExpect(status().isOk())
                .andExpect(content().json(expectedDefaultCustomerCart));
    }

}
