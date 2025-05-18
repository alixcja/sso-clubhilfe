package de.alixcja.clubhilfe.requestserivce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class AdRequest extends AbstractBaseRequest {

  @ManyToOne
  private Server server;

  private String ownerName;

  private int size;

  private String description;

  private String homepageUrl;

  private String youtubeUrl;

  private boolean isArchived = false;

  public AdRequest() {
  }

  public AdRequest(String clubName, String instagramUrl, String emailAddress, boolean notifyViaInstagram, boolean notifyViaEmail, String message, Server server, String ownerName, int size, String description, String homepageUrl, String youtubeUrl) {
    super(clubName, instagramUrl, emailAddress, notifyViaInstagram, notifyViaEmail, message);
    this.server = server;
    this.ownerName = ownerName;
    this.size = size;
    this.description = description;
    this.homepageUrl = homepageUrl;
    this.youtubeUrl = youtubeUrl;
  }

  public Server getServer() {
    return server;
  }

  public void setServer(Server server) {
    this.server = server;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getHomepageUrl() {
    return homepageUrl;
  }

  public void setHomepageUrl(String homepageUrl) {
    this.homepageUrl = homepageUrl;
  }

  public String getYoutubeUrl() {
    return youtubeUrl;
  }

  public void setYoutubeUrl(String youtubeUrl) {
    this.youtubeUrl = youtubeUrl;
  }

  public boolean isArchived() {
    return isArchived;
  }

  public void setArchived(boolean archived) {
    isArchived = archived;
  }
}
