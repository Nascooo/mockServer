package org.example.mockserver.config;

import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;

@Configuration
public class MockConfiguration {

    @Value("${mock.port.number}")
    private Integer portNumber;

    @Bean
    public ClientAndServer clientAndServer() {
        return startClientAndServer(portNumber);
    }
}
