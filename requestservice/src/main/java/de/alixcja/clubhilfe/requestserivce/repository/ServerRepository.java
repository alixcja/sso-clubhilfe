package de.alixcja.clubhilfe.requestserivce.repository;

import de.alixcja.clubhilfe.requestserivce.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {
}
