package de.alixcja.clubhilfe.requestserivce.controller;

import de.alixcja.clubhilfe.requestserivce.entity.ProfilePictureRequest;
import de.alixcja.clubhilfe.requestserivce.entity.RequestStatus;
import de.alixcja.clubhilfe.requestserivce.repository.ProfilePictureRepository;
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
public class ProfilePictureRequestController {

  // TODO: Make request afterwards editable
  @Autowired
  ProfilePictureRepository profilePictureRepository;

  @GetMapping("/profile-picture-requests")
  public Page<ProfilePictureRequest> fetchAllRequests(@ModelAttribute GetQueryParameters parameters) {
    Pageable pageable;
    if (parameters.getSortBy() != null && parameters.getDirection() != null) {
      Sort sort = parameters.getDirection().equalsIgnoreCase("desc") ?
              Sort.by(parameters.getSortBy()).descending() :
              Sort.by(parameters.getSortBy()).ascending();
      pageable = PageRequest.of(parameters.getPage(), parameters.getSize(), sort);
    } else {
      pageable = PageRequest.of(parameters.getPage(), parameters.getSize());
    }

    return profilePictureRepository.findAll(pageable);
  }

  @GetMapping("/profile-picture-requests/{id}")
  public ProfilePictureRequest fetchProfilePictureRequestById(@PathVariable("id") Long id) {
    return profilePictureRepository.findById(id).orElseThrow(getProfilePictureRequestByIdNotFound(id));
  }

  @PostMapping("/profile-picture-requests")
  public ResponseEntity<ProfilePictureRequest> createAdRequest(@RequestBody ProfilePictureRequest pro) {
    ProfilePictureRequest saved = profilePictureRepository.save(pro);
    return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(saved);
  }

  // TODO: Person who updates status should be set as "processedBy"
  @PutMapping("/profile-picture-requests/{id}")
  public ResponseEntity<?> updateStatusAdRequestById(@PathVariable("id") Long id, @RequestBody RequestStatus status) {
    ProfilePictureRequest profilePictureRequestRequestToUpdate = profilePictureRepository.findById(id).orElseThrow(getProfilePictureRequestByIdNotFound(id));
    profilePictureRequestRequestToUpdate.setStatus(status);
    profilePictureRepository.save(profilePictureRequestRequestToUpdate);
    return ResponseEntity.noContent().build();
  }

  private static Supplier<ResponseStatusException> getProfilePictureRequestByIdNotFound(Long id) {
    String exceptionMessage = String.format("Ad request with id %d not found", id);
    return () -> new ResponseStatusException(HttpStatus.NOT_FOUND, exceptionMessage);
  }
}
