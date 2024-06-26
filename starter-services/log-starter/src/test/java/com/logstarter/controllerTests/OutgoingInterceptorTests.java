package com.logstarter.controllerTests;

import com.logstarter.filters.OutgoingInterceptor;
import com.logstarter.services.SampleServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class OutgoingInterceptorTests {

    private static final String mockUrl = "http://mock.com";
    private static final String mockResponse = "Response";

    private final SampleServiceImpl sampleService = new SampleServiceImpl();
    private final RestTemplate restTemplate = sampleService.getRestTemplate();
    private final MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

    @Test
    @SneakyThrows
    public void testOutgoingInterceptorIsOk() {

        mockServer.expect(requestTo(mockUrl))
            .andRespond(withSuccess(mockResponse, org.springframework.http.MediaType.TEXT_PLAIN));

        OutgoingInterceptor outgoingInterceptor = sampleService.getOutgoingInterceptor();
        sampleService.requestOtherAppAndReceiveResponse(mockUrl);

        Map<String, String> logMessages = outgoingInterceptor.getLogMessages();

        assertThat(logMessages.get("Code of response to outgoing request")).isEqualTo("200 OK");
        assertThat(logMessages.get("Outgoing request method")).isEqualTo("GET");
        assertThat(logMessages.get("Outgoing request headers")).isNotNull();
        assertThat(logMessages.get("Outgoing request duration, ms.")).isNotNull();
        assertThat(logMessages.get("Headers of response to outgoing request")).isNotNull();
        assertThat(logMessages.get("Outgoing request URL"))
            .isEqualTo("http://mock.com");
    }
}


