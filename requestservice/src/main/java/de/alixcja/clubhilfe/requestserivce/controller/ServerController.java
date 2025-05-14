package de.alixcja.clubhilfe.requestserivce.controller;

import de.alixcja.clubhilfe.requestserivce.entity.Server;
import de.alixcja.clubhilfe.requestserivce.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.Supplier;

@RestController
public class ServerController {

  @Autowired
  ServerRepository serverRepository;

  @GetMapping("/servers")
  public List<Server> fetchAllServers() {
    return serverRepository.findAll();
  }

  @GetMapping("/servers/{id}")
  public Server fetchServerById(@PathVariable("id") Long id) {
    return serverRepository.findById(id).orElseThrow(getServerByIdNotFound(id));
  }

  @PostMapping("/servers")
  public ResponseEntity<Server> createServer(@RequestBody Server server) {
    Server save = serverRepository.save(server);
    return ResponseEntity.status(HttpStatus.CREATED).body(save);
  }

  @PutMapping("/servers/{id}")
  public Server updateServerById(@PathVariable Long id, @RequestBody Server update) {
    return serverRepository.findById(id)
            .map(server -> updateServer(update, server))
            .orElseThrow(getServerByIdNotFound(id));
  }

  @PutMapping("/servers/{id}/archive")
  public ResponseEntity<?> archiveServerById(@PathVariable Long id) {
    Server server = serverRepository.findById(id)
            .orElseThrow(getServerByIdNotFound(id));

    server.setIsArchived(true);
    serverRepository.save(server);

    return ResponseEntity.noContent().build();
  }

  private static Supplier<ResponseStatusException> getServerByIdNotFound(Long id) {
    String exceptionMessage = String.format("Server with id %d not found", id);
    return () -> new ResponseStatusException(HttpStatus.NOT_FOUND, exceptionMessage);
  }

  private Server updateServer(Server update, Server server) {
    server.setServerName(update.getServerName());
    server.setServerNumber(server.getServerNumber());
    return serverRepository.save(server);
  }
}
