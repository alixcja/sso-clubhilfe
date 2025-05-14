package de.alixcja.clubhilfe.requestserivce.controller;

import de.alixcja.clubhilfe.requestserivce.entity.AdRequest;
import de.alixcja.clubhilfe.requestserivce.entity.RequestStatus;
import de.alixcja.clubhilfe.requestserivce.entity.Server;
import de.alixcja.clubhilfe.requestserivce.repository.AdRequestRepository;
import de.alixcja.clubhilfe.requestserivce.repository.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.Supplier;

@RestController
public class AdRequestController {

  @Autowired
  AdRequestRepository adRequestRepository;

  @GetMapping("/ad-requests")
  public List<AdRequest> fetchAllAdRequests(@RequestParam(required = false) RequestStatus status, @RequestParam(required = false) Boolean wasDeclined) {
    if (status != null && wasDeclined != null) {
      adRequestRepository.findByStatusAndWasDeclined(status, wasDeclined);
    }
    if (wasDeclined != null) {
      adRequestRepository.findByWasDeclined(wasDeclined);
    }
    if (status != null) {
      adRequestRepository.findByStatus(status);
    }
    return adRequestRepository.findAll();
  }

  @GetMapping("/ad-requests/{id}")
  public AdRequest fetchAdRequestById(@PathVariable("id") Long id) {
    return adRequestRepository.findById(id).orElseThrow(getAdRequestByIdNotFound(id));
  }

  @PostMapping("/ad-requests")
  public ResponseEntity<AdRequest> createAdRequest(@RequestBody AdRequest request) {
    AdRequest saved = adRequestRepository.save(request);
    return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(saved);
  }

  @PutMapping("/ad-requests/{id}/decline")
  public ResponseEntity<?> updateAdRequestById(@PathVariable("id") Long id) {
    AdRequest adRequestToUpdate = adRequestRepository.findById(id).orElseThrow(getAdRequestByIdNotFound(id));
    adRequestToUpdate.setWasDeclined(true);
    adRequestRepository.save(adRequestToUpdate);
    return ResponseEntity.noContent().build();
  }

  // TODO: Implement put endpoint to update status of request

  private static Supplier<ResponseStatusException> getAdRequestByIdNotFound(Long id) {
    String exceptionMessage = String.format("Ad request with id %d not found", id);
    return () -> new ResponseStatusException(HttpStatus.NOT_FOUND, exceptionMessage);
  }
}
