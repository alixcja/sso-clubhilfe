package de.alixcja.clubhilfe.requestserivce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.alixcja.clubhilfe.requestserivce.entity.AdRequest;
import de.alixcja.clubhilfe.requestserivce.entity.Server;
import de.alixcja.clubhilfe.requestserivce.repository.AdRequestRepository;
import de.alixcja.clubhilfe.requestserivce.repository.ServerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdRequestControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private AdRequestRepository adRequestRepository;
  private Server winterStar;

  @Test
  void shouldReturnAdRequestById1() throws Exception {
    winterStar = new Server("Winter Start", 1);

    String message = "Wir, die Gold Roses Family, sind ein aktiver und liebevoll geführter Club mit viel Herzblut und Teamgeist.\n" +
            "Nach einem frischen Neustart vor wenigen Tagen freuen wir uns über neue Gesichter, die unsere Gemeinschaft bereichern möchten. Gegründet wurde der Club von Luna Goldlake auf dem Server Spring Star.\n" +
            "\n" +
            "Unser Hauptfokus liegt auf Dressur, doch auch Bodenarbeit, Ausritte und kleine Turniere stehen regelmäßig auf dem Programm.\n" +
            "Unser Cluboutfit erstrahlt in Gold und Rosé, passend zu unserem Namen, und als Clubpferd haben wir den Lusitano gewählt.\n" +
            "\n" +
            "Neben dem Ingame-Clubleben sind wir auch auf Discord, in einer WhatsApp-Gruppe, auf Instagram (@goldroses.family) und über unsere eigene Homepage (www.goldrosesfamily.club) erreichbar.\n" +
            "Gemeinsam wachsen, lachen, trainieren – das ist die Gold Roses Family.";
    AdRequest goldRosesFamily = new AdRequest("Gold Roses Family", "www.instagram.com/gold-roses-family", "goldrosesfamily@gmail.com", true, true, null, winterStar, "Luna Goldlake", 5, message, "www.goldrosesfamily.club", "www.youtube.com/gold-roses-family");

    Mockito.when(adRequestRepository.findById(1L)).thenReturn(Optional.of(goldRosesFamily));
    String responseBody = objectMapper.writeValueAsString(goldRosesFamily);

    this.mockMvc
            .perform(get("/ad-requests/1"))
            .andExpect(status().isOk())
            .andExpect(content()
                    .string(containsString(responseBody)));
  }

}