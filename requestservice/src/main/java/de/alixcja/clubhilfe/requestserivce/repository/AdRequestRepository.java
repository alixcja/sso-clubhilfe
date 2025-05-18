package de.alixcja.clubhilfe.requestserivce.repository;

import de.alixcja.clubhilfe.requestserivce.entity.AdRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRequestRepository extends JpaRepository<AdRequest, Long> {
}
