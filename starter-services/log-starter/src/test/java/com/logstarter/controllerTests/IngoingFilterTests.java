package com.logstarter.controllerTests;

import com.logstarter.filters.IngoingFilter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class IngoingFilterTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IngoingFilter ingoingFilter;

    @Test
    @SneakyThrows
    public void ingoingFilterIsOkTest() {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/samples/request-and-receive-response").header("SomeHeader", "HeaderValue");
        mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk());

        Map<String, String> logMessages = ingoingFilter.getLogMessages();

        assertThat(logMessages.get("Code of response to ingoing request")).isEqualTo("200");
        assertThat(logMessages.get("Ingoing request method")).isEqualTo("GET");
        assertThat(logMessages.get("Ingoing request headers")).isEqualTo("SomeHeader: HeaderValue");
        assertThat(logMessages.get("Ingoing request duration, ms.")).isNotNull();
        assertThat(logMessages.get("Headers of response to ingoing request")).isNotNull();
        assertThat(logMessages.get("Ingoing request URL"))
            .isEqualTo("http://localhost/samples/request-and-receive-response");
    }

    @Test
    @SneakyThrows
    public void ingoingFilterInternalServerErrorTest() {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/samples/request-and-receive-error").header("SomeHeader", "HeaderValue");
        mockMvc.perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isInternalServerError());

        Map<String, String> logMessages = ingoingFilter.getLogMessages();

        assertThat(logMessages.get("Code of response to ingoing request")).isEqualTo("500");
        assertThat(logMessages.get("Ingoing request method")).isEqualTo("GET");
        assertThat(logMessages.get("Ingoing request headers")).isEqualTo("SomeHeader: HeaderValue");
        assertThat(logMessages.get("Ingoing request duration, ms.")).isNotNull();
        assertThat(logMessages.get("Headers of response to ingoing request")).isNotNull();
        assertThat(logMessages.get("Ingoing request URL"))
            .isEqualTo("http://localhost/samples/request-and-receive-error");
    }
}