package com.securityjwt.controllers;

import com.securityjwt.enums.Role;
import com.securityjwt.models.User;
import com.securityjwt.security.TokenService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SampleControllerAuthTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenService tokenService;

    private final User performer = User.builder()
        .username("performerUsername")
        .password("performerPassword")
        .roles(Collections.singleton(Role.PERFORMER))
        .build();
    private final User customer = User.builder()
        .username("customerUsername")
        .password("customerPassword")
        .roles(Collections.singleton(Role.CUSTOMER))
        .build();
    private final User user = User.builder()
        .username("userUsername")
        .password("userPassword")
        .roles(Collections.singleton(Role.USER))
        .build();

    @Test
    @SneakyThrows
    void checkPerformerAccessSuccess() {
        mockMvc.perform(get("/api/products/shop/performer-board/check-access")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenService.generateAccessToken(performer)))
            .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void checkCustomerAccessSuccess() {
        mockMvc.perform(get("/api/products/shop/customer-board/check-access")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenService.generateAccessToken(customer)))
            .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void checkUserAccessSuccess() {
        mockMvc.perform(get("/api/products/shop/demo/check-user-access")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenService.generateAccessToken(user)))
            .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void checkPerformerOrCustomerAccess() {
        mockMvc.perform(get("/api/products/shop/statistics/check-performer-and-customer-access")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenService.generateAccessToken(performer)))
            .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void checkPerformerAccessForbidden() {
        mockMvc.perform(get("/api/products/shop/performer-board/check-access")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenService.generateAccessToken(customer)))
            .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void checkCustomerAccessForbidden() {
        mockMvc.perform(get("/api/products/shop/customer-board/check-access")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenService.generateAccessToken(performer)))
            .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void checkPerformerOrCustomerForbidden() {
        mockMvc.perform(get("/api/products/shop/statistics/check-performer-and-customer-access")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenService.generateAccessToken(user)))
            .andExpect(status().isForbidden());
    }

}
