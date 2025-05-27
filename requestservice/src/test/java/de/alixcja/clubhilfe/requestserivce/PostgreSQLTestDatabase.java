package de.alixcja.clubhilfe.requestserivce;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public abstract class PostgreSQLTestDatabase {
  private static final PostgreSQLContainer<?> postgresContainer;

  static {
    postgresContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("test")
            .withUsername("user")
            .withPassword("pass");
    postgresContainer.start();

    // Optionally set System properties so Spring picks them up
    System.setProperty("DB_URL", postgresContainer.getJdbcUrl());
    System.setProperty("DB_USERNAME", postgresContainer.getUsername());
    System.setProperty("DB_PASSWORD", postgresContainer.getPassword());
  }

  public static PostgreSQLContainer<?> getInstance() {
    return postgresContainer;

  }
}
