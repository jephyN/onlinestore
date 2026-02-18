package com.bm.store.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {



    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithAnonymousUser
    void connects_whenRequestHasNeitherUserNorPassword_shouldReturn401() throws Exception {
        this.mockMvc.perform(post("/api/account"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void connects_whenRequestHasInvalidUserAndPassword_shouldReturn401() throws Exception {
        this.mockMvc.perform(post("/api/account").with(httpBasic("Nope", "wrongPass")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void connects_whenRequestHasInvalidPassword_shouldReturn401() throws Exception {
        this.mockMvc.perform(post("/api/account").with(httpBasic("user", "wrongPass")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void connects_whenRequestHasInvalidUser_shouldReturn401() throws Exception {
        this.mockMvc.perform(post("/api/account").with(httpBasic("WrongUser", "password")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails()
    void connects_whenRequestHasValidUserAndPassword_shouldReturnConnected() throws Exception {
        this.mockMvc.perform(post("/api/account").with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(content().string("Connected!!"));
    }

    @Test
    @WithAnonymousUser
    void disconnects_whenRequestHasNeitherUserNorPassword_shouldReturn401() throws Exception {
        this.mockMvc.perform(delete("/api/account"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void disconnects_whenRequestHasInvalidUserAndPassword_shouldReturn401() throws Exception {
        this.mockMvc.perform(delete("/api/account").with(httpBasic("Nope", "wrongPass")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void disconnects_whenRequestHasInvalidPassword_shouldReturn401() throws Exception {
        this.mockMvc.perform(delete("/api/account").with(httpBasic("user", "wrongPass")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void disconnects_whenRequestHasInvalidUser_shouldReturn401() throws Exception {
        this.mockMvc.perform(delete("/api/account").with(httpBasic("WrongUser", "password")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails()
    void disconnects_whenRequestHasValidUserAndPassword_shouldReturnDisconnected() throws Exception {
        this.mockMvc.perform(delete("/api/account").with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(content().string("Disconnected!!"));
    }

}
