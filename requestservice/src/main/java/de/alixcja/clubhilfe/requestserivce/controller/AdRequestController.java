package de.alixcja.clubhilfe.requestserivce.controller;

import de.alixcja.clubhilfe.requestserivce.entity.AdRequest;
import de.alixcja.clubhilfe.requestserivce.entity.Server;
import de.alixcja.clubhilfe.requestserivce.repository.AdRequestRepository;
import de.alixcja.clubhilfe.requestserivce.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdRequestController {

  @Autowired
  AdRequestRepository adRequestRepository;

  @GetMapping("/adrequest")
  public List<AdRequest> fetchAllAdRequests() {
    return adRequestRepository.findAll();
  }

  @GetMapping("/adrequest/{id}")
  public AdRequest fetchAdRequestById(@PathVariable("id") Long id) {
    return adRequestRepository.findById(id).orElseThrow();
  }

  @PostMapping("/adrequest")
  public AdRequest createAdRequest(@RequestBody AdRequest request) {
    return adRequestRepository.save(request);
  }
}
