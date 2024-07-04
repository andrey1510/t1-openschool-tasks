package com.securityjwt.controllers;

import com.securityjwt.models.User;
import com.securityjwt.services.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerRegistrationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @SneakyThrows
    @Test
    void createUserWithSuccess() {

        when(userService.findByUsername("sampleUsername")).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/users/register")
                .param("username", "sampleUsername")
                .param("password", "samplePassword")
                .param("customerRole", "true")
                .param("performerRole", "false")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(content().string("Пользователь успешно зарегистрирован."));

        verify(userService, times(1)).findByUsername("sampleUsername");
        verify(userService, times(1)).createUser(any(User.class));
    }

    @SneakyThrows
    @Test
    void createUserWithError() {

        when(userService.findByUsername("existingUsername")).thenReturn(Optional.of(new User()));

        mockMvc.perform(post("/api/users/register")
                .param("username", "existingUsername")
                .param("password", "samplePassword")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("Пользователь с таким именем уже существует."));

        verify(userService, times(1)).findByUsername("existingUsername");
        verify(userService, never()).createUser(any(User.class));
    }

}
