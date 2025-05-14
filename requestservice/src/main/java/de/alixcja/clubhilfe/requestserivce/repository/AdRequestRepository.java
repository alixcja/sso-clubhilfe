package de.alixcja.clubhilfe.requestserivce.repository;

import de.alixcja.clubhilfe.requestserivce.entity.AdRequest;
import de.alixcja.clubhilfe.requestserivce.entity.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRequestRepository extends JpaRepository<AdRequest, Long> {
  List<AdRequest> findByStatusAndWasDeclined(RequestStatus status, boolean wasDeclined);

  List<AdRequest> findByStatus(RequestStatus status);

  List<AdRequest> findByWasDeclined(boolean wasDeclined);
}
