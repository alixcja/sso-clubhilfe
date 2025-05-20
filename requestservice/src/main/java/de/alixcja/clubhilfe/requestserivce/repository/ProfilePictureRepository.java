package de.alixcja.clubhilfe.requestserivce.repository;

import de.alixcja.clubhilfe.requestserivce.entity.ProfilePictureRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilePictureRepository extends JpaRepository<ProfilePictureRequest, Long> {
}
