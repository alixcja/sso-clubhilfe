package de.alixcja.clubhilfe.requestserivce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.alixcja.clubhilfe.requestserivce.entity.Server;
import de.alixcja.clubhilfe.requestserivce.repository.ServerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ServerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private ServerRepository serverRepository;

  @Test
  void shouldReturnServerById1() throws Exception {
    Server newServer = new Server("Winter Star", 1);
    Mockito.when(serverRepository.findById(1L)).thenReturn(Optional.of(newServer));
    String responseBody = objectMapper.writeValueAsString(newServer);

    this.mockMvc
            .perform(get("/servers/1"))
            .andExpect(status().isOk())
            .andExpect(content()
                    .string(containsString(responseBody)));
  }

  @Test
  void shouldReturn404DueNonExistingId() throws Exception {
    Mockito.when(serverRepository.findById(1L)).thenReturn(Optional.empty());

    this.mockMvc
            .perform(get("/servers/1"))
            .andDo(print())
            .andExpect(status()
                    .isNotFound());
  }

  @Test
  void shouldCreateServer() throws Exception {
    Server newServer = new Server("Winter Star", 1);
    Mockito.when(serverRepository.save(Mockito.any(Server.class))).thenReturn(newServer);
    String responseBody = objectMapper.writeValueAsString(newServer);

    mockMvc.perform(post("/servers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(responseBody))
            .andExpect(status().isCreated())
            .andExpect(content()
              .string(containsString(responseBody)));
  }

  @Test
  void shouldUpdateServerById() throws Exception {
    Server server = new Server("Winter Star", 1);
    Server updatedServer = new Server("Autumn Star", 1);

    Mockito.when(serverRepository.findById(1L)).thenReturn(Optional.of(server));
    Mockito.when(serverRepository.save(Mockito.any(Server.class))).thenReturn(updatedServer);

    String requestBody = objectMapper.writeValueAsString(server);
    String responseBody = objectMapper.writeValueAsString(updatedServer);

    mockMvc.perform(put("/servers/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().json(responseBody));
  }

  @Test
  void shouldNotUpdateServerById() throws Exception {
    Mockito.when(serverRepository.findById(1L)).thenReturn(Optional.empty());

    Server updatedServer = new Server("Autumn Star", 1);
    String requestBody = objectMapper.writeValueAsString(updatedServer);


    mockMvc.perform(put("/servers/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(status().isNotFound());
  }

  @Test
  void shouldArchiveServerById() throws Exception {
    Server archivedServer = new Server("Autumn Star", 1);

    Mockito.when(serverRepository.findById(1L)).thenReturn(Optional.of(archivedServer));
    Mockito.when(serverRepository.save(archivedServer)).thenReturn(archivedServer);

    String requestBody = objectMapper.writeValueAsString(archivedServer);

    mockMvc.perform(put("/servers/1/archive")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(status().isNoContent());
  }

  @Test
  void shouldNotArchiveServerById() throws Exception {
    Mockito.when(serverRepository.findById(1L)).thenReturn(Optional.empty());

    Server updatedServer = new Server("Autumn Star", 1);
    String requestBody = objectMapper.writeValueAsString(updatedServer);

    mockMvc.perform(put("/servers/1/archive")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(status().isNotFound());
  }
}