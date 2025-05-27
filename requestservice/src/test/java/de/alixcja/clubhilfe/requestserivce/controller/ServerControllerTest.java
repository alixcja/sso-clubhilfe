package de.alixcja.clubhilfe.requestserivce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.alixcja.clubhilfe.requestserivce.PostgreSQLTestDatabase;
import de.alixcja.clubhilfe.requestserivce.entity.Server;
import de.alixcja.clubhilfe.requestserivce.repository.ServerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ServerControllerTest extends PostgreSQLTestDatabase {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ServerRepository serverRepository;

  private Server winterStar;

  @BeforeEach
  @Transactional
  void setUp() {
    Server newServer = new Server("Winter Star", 1);
    winterStar = serverRepository.save(newServer);
  }

  @Test
  void shouldReturnServerById1() throws Exception {
    String responseBody = objectMapper.writeValueAsString(winterStar);

    this.mockMvc
            .perform(get("/servers/" + winterStar.getId()))
            .andExpect(status().isOk())
            .andExpect(content()
                    .string(containsString(responseBody)));
  }

  @Test
  void shouldReturn404DueNonExistingId() throws Exception {
    this.mockMvc
            .perform(get("/servers/9999"))
            .andExpect(status()
                    .isNotFound());
  }

  @Test
  void shouldCreateServer() throws Exception {
    Server newServer = new Server("Winter Star", 1);
    String requestBody = objectMapper.writeValueAsString(newServer);

    mockMvc.perform(post("/servers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(status().isCreated());

    List<Server> servers = serverRepository.findAll();
    assertEquals(2, servers.size());
  }

  @Test
  void shouldUpdateServerById() throws Exception {
    Server updatedServer = new Server("Autumn Star", 1);

    String requestBody = objectMapper.writeValueAsString(updatedServer);

    mockMvc.perform(put("/servers/" + winterStar.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody));

    Optional<Server> byId = serverRepository.findById(winterStar.getId());
    assertEquals("Autumn Star", byId.get().getServerName());
    assertEquals(1, byId.get().getServerNumber());
  }

  @Test
  void shouldNotUpdateServerById() throws Exception {
    Server updatedServer = new Server("Autumn Star", 1);
    String requestBody = objectMapper.writeValueAsString(updatedServer);

    mockMvc.perform(put("/servers/9999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(status().isNotFound());
  }

  @Test
  void shouldArchiveServerById() throws Exception {
    mockMvc.perform(put("/servers/" + winterStar.getId() + "/archive")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
  }

  @Test
  void shouldNotArchiveServerById() throws Exception {
    mockMvc.perform(put("/servers/9999/archive")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
  }

  @AfterEach
  void tearDown() {
    serverRepository.deleteAll();
  }
}