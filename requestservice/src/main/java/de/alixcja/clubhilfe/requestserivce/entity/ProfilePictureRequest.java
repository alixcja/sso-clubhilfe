package de.alixcja.clubhilfe.requestserivce.entity;

import jakarta.persistence.Entity;

@Entity
public class ProfilePictureRequest extends AbstractBaseRequest {
  private String description;

  public ProfilePictureRequest() {
  }

  public ProfilePictureRequest(String clubName, String instagramUrl, String emailAddress, boolean notifyViaInstagram, boolean notifyViaEmail, String message, String description) {
    super(clubName, instagramUrl, emailAddress, notifyViaInstagram, notifyViaEmail, message);
    this.description = description;
  }

  public ProfilePictureRequest(String clubName, String instagramUrl, String emailAddress, boolean notifyViaInstagram, boolean notifyViaEmail, String description) {
    super(clubName, instagramUrl, emailAddress, notifyViaInstagram, notifyViaEmail, null);
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
