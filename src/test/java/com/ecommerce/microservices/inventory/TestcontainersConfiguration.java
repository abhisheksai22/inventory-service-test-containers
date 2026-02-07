package com.ecommerce.microservices.inventory;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    public static PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("inventory_db")
                .withUsername("postgres")
                .withPassword("postgres");
    }

    @Bean
    @ServiceConnection(name = "redis")
    public static GenericContainer<?> redisContainer() {
        return new GenericContainer<>("redis:7")
                .withExposedPorts(6379);
    }
}



