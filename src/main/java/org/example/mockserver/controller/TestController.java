package org.example.mockserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.mockserver.model.RequestModel;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.StringBody.exact;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final ClientAndServer clientAndServer;

    @PutMapping("/test")
    public String test(HttpServletRequest httpRequest, @RequestBody RequestModel model) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(model.getRequest());
        String responseBody = mapper.writeValueAsString(model.getResponse());
        clientAndServer
                .when(
                        request()
                                .withMethod(model.getMethod())
                                .withPath("/validate")
                                .withHeader("\"Content-type\", \"application/json\"")
                                .withBody(exact(requestBody)),
                        exactly(model.getCount()))
                .respond(
                        response()
                                .withStatusCode(model.getStatusCode())
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody(responseBody)
                                .withDelay(TimeUnit.SECONDS, 1)
                );
        return requestBody;

    }


}
