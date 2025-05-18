package de.alixcja.clubhilfe.requestserivce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.alixcja.clubhilfe.requestserivce.entity.AdRequest;
import de.alixcja.clubhilfe.requestserivce.entity.RequestStatus;
import de.alixcja.clubhilfe.requestserivce.entity.Server;
import de.alixcja.clubhilfe.requestserivce.repository.AdRequestRepository;
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

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AdRequestControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private AdRequestRepository adRequestRepository;

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
  @Transactional
  void shouldReturnAdRequestById1() throws Exception {

    String message = "Wir, die Gold Roses Family, sind ein aktiver und liebevoll geführter Club mit viel Herzblut und Teamgeist.\n" +
            "Nach einem frischen Neustart vor wenigen Tagen freuen wir uns über neue Gesichter, die unsere Gemeinschaft bereichern möchten. Gegründet wurde der Club von Luna Goldlake auf dem Server Spring Star.\n" +
            "\n" +
            "Unser Hauptfokus liegt auf Dressur, doch auch Bodenarbeit, Ausritte und kleine Turniere stehen regelmäßig auf dem Programm.\n" +
            "Unser Cluboutfit erstrahlt in Gold und Rosé, passend zu unserem Namen, und als Clubpferd haben wir den Lusitano gewählt.\n" +
            "\n" +
            "Neben dem Ingame-Clubleben sind wir auch auf Discord, in einer WhatsApp-Gruppe, auf Instagram (@goldroses.family) und über unsere eigene Homepage (www.goldrosesfamily.club) erreichbar.\n" +
            "Gemeinsam wachsen, lachen, trainieren – das ist die Gold Roses Family.";
    AdRequest goldRosesFamily = new AdRequest("Gold Roses Family", "www.instagram.com/gold-roses-family", "goldrosesfamily@gmail.com", true, true, null, winterStar, "Luna Goldlake", 5, message, "www.goldrosesfamily.club", "www.youtube.com/gold-roses-family");
    adRequestRepository.save(goldRosesFamily);

    String responseBody = objectMapper.writeValueAsString(goldRosesFamily);

    this.mockMvc
            .perform(get("/ad-requests/" + goldRosesFamily.getId()))
            .andExpect(status().isOk())
            .andExpect(content()
                    .string(containsString(responseBody)));
  }

  @Test
  void shouldReturnAdRequestsSortedByClubNameAsc() throws Exception {
    AdRequest bClub = new AdRequest();
    bClub.setClubName("Beta Club");
    AdRequest aClub = new AdRequest();
    aClub.setClubName("Alpha Club");
    adRequestRepository.saveAll(List.of(bClub, aClub));

    mockMvc.perform(get("/ad-requests")
                    .param("sortBy", "clubName")
                    .param("direction", "asc")
                    .param("page", "0")
                    .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].clubName").value("Alpha Club"))
            .andExpect(jsonPath("$.content[1].clubName").value("Beta Club"));
  }

  @Test
  void shouldReturnAdRequestsSortedByStatusDesc() throws Exception {
    AdRequest second  = new AdRequest();
    second.setClubName("Beta Club");
    second.setStatus(RequestStatus.PENDING);
    AdRequest first = new AdRequest();
    first .setClubName("Alpha Club");
    second.setStatus(RequestStatus.COMPLETED);
    adRequestRepository.saveAll(List.of(first, second));

    mockMvc.perform(get("/ad-requests")
                    .param("sortBy", "status")
                    .param("direction", "desc")
                    .param("page", "0")
                    .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].status").value("PENDING"))
            .andExpect(jsonPath("$.content[1].status").value("COMPLETED"));
  }

  @Test
  void shouldReturn404WhenAdRequestNotFound() throws Exception {
    mockMvc.perform(get("/ad-requests/9999"))
            .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void shouldCreateNewAdRequest() throws Exception {

    String message = "Wir, die Gold Roses Family, sind ein aktiver und liebevoll geführter Club mit viel Herzblut und Teamgeist.\n" +
            "Nach einem frischen Neustart vor wenigen Tagen freuen wir uns über neue Gesichter, die unsere Gemeinschaft bereichern möchten. Gegründet wurde der Club von Luna Goldlake auf dem Server Spring Star.\n" +
            "\n" +
            "Unser Hauptfokus liegt auf Dressur, doch auch Bodenarbeit, Ausritte und kleine Turniere stehen regelmäßig auf dem Programm.\n" +
            "Unser Cluboutfit erstrahlt in Gold und Rosé, passend zu unserem Namen, und als Clubpferd haben wir den Lusitano gewählt.\n" +
            "\n" +
            "Neben dem Ingame-Clubleben sind wir auch auf Discord, in einer WhatsApp-Gruppe, auf Instagram (@goldroses.family) und über unsere eigene Homepage (www.goldrosesfamily.club) erreichbar.\n" +
            "Gemeinsam wachsen, lachen, trainieren – das ist die Gold Roses Family.";
    AdRequest goldRosesFamily = new AdRequest("Gold Roses Family", "www.instagram.com/gold-roses-family", "goldrosesfamily@gmail.com", true, true, null, winterStar, "Luna Goldlake", 5, message, "www.goldrosesfamily.club", "www.youtube.com/gold-roses-family");

    String requestBody = objectMapper.writeValueAsString(goldRosesFamily);

    mockMvc.perform(post("/ad-requests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andExpect(status().isCreated());

    List<AdRequest> adRequests = adRequestRepository.findAll();
    assertEquals(1, adRequests.size());
  }

  @Test
  @Transactional
  void shouldUpdateStatusAdRequestById() throws Exception {
    AdRequest existing = new AdRequest();
    existing.setClubName("Club to update");
    existing.setStatus(RequestStatus.PENDING);
    existing = adRequestRepository.save(existing);

    String responseBody = objectMapper.writeValueAsString(RequestStatus.COMPLETED);

    mockMvc.perform(put("/ad-requests/" + existing.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(responseBody))
            .andExpect(status().isNoContent());

    AdRequest updated = adRequestRepository.findById(existing.getId()).orElseThrow();
    assertEquals(RequestStatus.COMPLETED, updated.getStatus());
  }

  @Test
  void shouldNotUpdateStatusByIdDueInvalidAdRequestId() throws Exception {
    String newStatusJson = objectMapper.writeValueAsString(RequestStatus.COMPLETED);

    mockMvc.perform(put("/ad-requests/99999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(newStatusJson))
            .andExpect(status().isNotFound());
  }

  @AfterEach
  @Transactional
  void tearDown() {
    adRequestRepository.deleteAll();
  }
}