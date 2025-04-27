package de.alixcja.clubhilfe.requestserivce.controller;

import de.alixcja.clubhilfe.requestserivce.entity.Server;
import de.alixcja.clubhilfe.requestserivce.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ServerController {

  @Autowired
  ServerRepository serverRepository;

  @GetMapping("/servers")
  public List<Server> fetchAllServers() {
    return serverRepository.findAll();
  }

  @GetMapping("/servers/{id}")
  public Server fetchServerById(@PathVariable ("id") Long id) {
    return serverRepository.findById(id).orElseThrow();
  }

  @PostMapping("/servers")
  public Server createServer(@RequestBody Server server) {
    return serverRepository.save(server);
  }
}
