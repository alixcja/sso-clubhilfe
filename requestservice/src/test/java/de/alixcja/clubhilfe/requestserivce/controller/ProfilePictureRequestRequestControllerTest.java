package de.alixcja.clubhilfe.requestserivce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.alixcja.clubhilfe.requestserivce.PostgreSQLTestDatabase;
import de.alixcja.clubhilfe.requestserivce.entity.ProfilePictureRequest;
import de.alixcja.clubhilfe.requestserivce.entity.RequestStatus;
import de.alixcja.clubhilfe.requestserivce.repository.ProfilePictureRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ContextConfiguration
class ProfilePictureRequestRequestControllerTest extends PostgreSQLTestDatabase {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ProfilePictureRepository profilePictureRepository;

  @Test
  void shouldReturnProfilePictureRequestById1() throws Exception {
    ProfilePictureRequest request = new ProfilePictureRequest("Gold Roses Family", "www.instagram.com/gold-roses-family", "goldrosesfamily@gmail.com", true, false, "Should be blue and contain a friesian with cylinder.");
    profilePictureRepository.save(request);

    String responseBody = objectMapper.writeValueAsString(request);

    mockMvc
            .perform(get("/profile-picture-requests/" + request.getId()))
            .andExpect(status().isOk())
            .andExpect(content()
                    .string(containsString(responseBody)));
  }

  @Test
  void shouldReturnProfilePictureRequestSortedByClubNameDesc() throws Exception {
    ProfilePictureRequest request1 = new ProfilePictureRequest();
    request1.setClubName("Alpha Club");
    ProfilePictureRequest request2 = new ProfilePictureRequest();
    request2.setClubName("Beta Club");

    profilePictureRepository.saveAll(List.of(request1, request2));

    this.mockMvc
            .perform(get("/profile-picture-requests")
                    .param("sortBy", "clubName")
                    .param("direction", "desc")
                    .param("page", "0")
                    .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].clubName").value("Beta Club"))
            .andExpect(jsonPath("$.content[1].clubName").value("Alpha Club"));
  }

  @Test
  void shouldReturnProfilePictureRequestsSortedByStatusAsc() throws Exception {
    ProfilePictureRequest request1 = new ProfilePictureRequest();
    request1.setClubName("Alpha Club");
    request1.setStatus(RequestStatus.REJECTED);
    ProfilePictureRequest request2 = new ProfilePictureRequest();
    request2.setClubName("Beta Club");
    request2.setStatus(RequestStatus.PROCESSING);

    profilePictureRepository.saveAll(List.of(request1, request2));

    mockMvc.perform(get("/profile-picture-requests")
                    .param("sortBy", "status")
                    .param("direction", "asc")
                    .param("page", "0")
                    .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].status").value("PROCESSING"))
            .andExpect(jsonPath("$.content[1].status").value("REJECTED"));
  }

  @Test
  void shouldReturn404WhenProfilePictureRequestNotFound() throws Exception {
    mockMvc
            .perform(get("/profile-picture-requests/9999"))
            .andExpect(status()
                    .isNotFound());
  }

  @Test
  void shouldCreateNewProfilePictureRequest() throws Exception {
    ProfilePictureRequest request = new ProfilePictureRequest("Gold Roses Family", "www.instagram.com/gold-roses-family", "goldrosesfamily@gmail.com", true, false, "Should be blue and contain a friesian with cylinder.");

    String requestBody = objectMapper.writeValueAsString(request);

    mockMvc.perform(post("/profile-picture-requests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated());

    List<ProfilePictureRequest> profilePictureRequestRequests = profilePictureRepository.findAll();
    assertEquals(1, profilePictureRequestRequests.size());
  }

  @Test
  void shouldUpdateStatusProfilePictureRequestById() throws Exception {
    ProfilePictureRequest existing = new ProfilePictureRequest();
    existing.setClubName("Club to update");
    existing.setStatus(RequestStatus.PENDING);
profilePictureRepository.save(existing);
    String responseBody = objectMapper.writeValueAsString(RequestStatus.COMPLETED);

    mockMvc.perform(put("/profile-picture-requests/" + existing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(responseBody))
            .andExpect(status().isNoContent());

    ProfilePictureRequest updated = profilePictureRepository.findById(existing.getId()).orElseThrow();
    assertEquals(RequestStatus.COMPLETED, updated.getStatus());
  }

  @Test
  void shouldNotUpdateStatusByIdDueInvalidProfilePictureRequestId() throws Exception {
    String newStatusJson = objectMapper.writeValueAsString(RequestStatus.COMPLETED);

    mockMvc.perform(put("/profile-picture-requests/99999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newStatusJson))
            .andExpect(status().isNotFound());
  }

  @AfterEach
  @Transactional
  void tearDown() {
    profilePictureRepository.deleteAll();
  }
}