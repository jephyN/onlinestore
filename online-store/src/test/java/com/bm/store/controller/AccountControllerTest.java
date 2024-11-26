package com.bm.store.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void connects_whenRequestHasNeitherUserNorPassword_shouldReturn401() throws Exception {
        this.mockMvc.perform(post("/api/account"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void connects_whenRequestHasInvalidUserAndPassword_shouldReturn401() throws Exception {
        this.mockMvc.perform(post("/api/account").with(httpBasic("Nope", "wrongPass")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void connects_whenRequestHasInvalidPassword_shouldReturn401() throws Exception {
        this.mockMvc.perform(post("/api/account").with(httpBasic("user", "wrongPass")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void connects_whenRequestHasInvalidUser_shouldReturn401() throws Exception {
        this.mockMvc.perform(post("/api/account").with(httpBasic("WrongUser", "password")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void connects_whenRequestHasValidUserAndPassword_shouldReturnConnected() throws Exception {
        this.mockMvc.perform(post("/api/account").with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(content().string("Connected!!"));
    }

    @Test
    public void disconnects_whenRequestHasNeitherUserNorPassword_shouldReturn401() throws Exception {
        this.mockMvc.perform(delete("/api/account"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void disconnects_whenRequestHasInvalidUserAndPassword_shouldReturn401() throws Exception {
        this.mockMvc.perform(delete("/api/account").with(httpBasic("Nope", "wrongPass")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void disconnects_whenRequestHasInvalidPassword_shouldReturn401() throws Exception {
        this.mockMvc.perform(delete("/api/account").with(httpBasic("user", "wrongPass")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void disconnects_whenRequestHasInvalidUser_shouldReturn401() throws Exception {
        this.mockMvc.perform(delete("/api/account").with(httpBasic("WrongUser", "password")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void disconnects_whenRequestHasValidUserAndPassword_shouldReturnDisconnected() throws Exception {
        this.mockMvc.perform(delete("/api/account").with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(content().string("Disconnected!!"));
    }

}
