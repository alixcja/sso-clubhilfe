package de.alixcja.clubhilfe.requestserivce;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public abstract class PostgreSQLTestDatabase {

  static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15")
          .withDatabaseName("test")
          .withUsername("user")
          .withPassword("pass");

  static {
    postgresContainer.start();
  }

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresContainer::getUsername);
    registry.add("spring.datasource.password", postgresContainer::getPassword);
  }
}
