package de.alixcja.clubhilfe.requestserivce.controller;

import de.alixcja.clubhilfe.requestserivce.entity.AdRequest;
import de.alixcja.clubhilfe.requestserivce.entity.RequestStatus;
import de.alixcja.clubhilfe.requestserivce.repository.AdRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.function.Supplier;

@RestController
public class AdRequestController {

  @Autowired
  AdRequestRepository adRequestRepository;

  @GetMapping("/ad-requests")
  public Page<AdRequest> fetchAllAdRequests(@ModelAttribute GetQueryParameters parameters) {

    Pageable pageable;
    if (parameters.getSortBy() != null && parameters.getDirection() != null) {
      Sort sort = parameters.getDirection().equalsIgnoreCase("desc") ?
              Sort.by(parameters.getSortBy()).descending() :
              Sort.by(parameters.getSortBy()).ascending();
      pageable = PageRequest.of(parameters.getPage(), parameters.getSize(), sort);
    } else {
      pageable = PageRequest.of(parameters.getPage(), parameters.getSize());
    }

    return adRequestRepository.findAll(pageable);
  }

  @GetMapping("/ad-requests/{id}")
  public AdRequest fetchAdRequestById(@PathVariable("id") Long id) {
    return adRequestRepository.findById(id).orElseThrow(getAdRequestByIdNotFound(id));
  }

  @PostMapping("/ad-requests")
  public ResponseEntity<AdRequest> createAdRequest(@RequestBody AdRequest request) {
    AdRequest saved = adRequestRepository.save(request);
    return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(saved);
  }

  // TODO: Person who updates status should be set as "processedBy"
  @PutMapping("/ad-requests/{id}")
  public ResponseEntity<?> updateStatusAdRequestById(@PathVariable("id") Long id, @RequestBody RequestStatus status) {
    AdRequest adRequestToUpdate = adRequestRepository.findById(id).orElseThrow(getAdRequestByIdNotFound(id));
    adRequestToUpdate.setStatus(status);
    adRequestRepository.save(adRequestToUpdate);
    return ResponseEntity.noContent().build();
  }

  private static Supplier<ResponseStatusException> getAdRequestByIdNotFound(Long id) {
    String exceptionMessage = String.format("Ad request with id %d not found", id);
    return () -> new ResponseStatusException(HttpStatus.NOT_FOUND, exceptionMessage);
  }
}
