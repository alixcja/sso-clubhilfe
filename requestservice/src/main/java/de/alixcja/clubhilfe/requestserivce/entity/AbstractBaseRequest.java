package de.alixcja.clubhilfe.requestserivce.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
abstract class AbstractBaseRequest {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String clubName;

  private String instagramUrl;

  private String emailAddress;

  private boolean notifyViaInstagram = false;

  private boolean notifyViaEmail = false;

  private String message;

  private RequestStatus status = RequestStatus.PENDING;

  private Long isProcessedBy;

  public AbstractBaseRequest() {
  }

  public AbstractBaseRequest(String clubName, String instagramUrl, String emailAddress, boolean notifyViaInstagram, boolean notifyViaEmail, String message) {
    this.clubName = clubName;
    this.instagramUrl = instagramUrl;
    this.emailAddress = emailAddress;
    this.notifyViaInstagram = notifyViaInstagram;
    this.notifyViaEmail = notifyViaEmail;
    this.message = message;
  }

  public Long getId() {
    return id;
  }

  public String getClubName() {
    return clubName;
  }

  public void setClubName(String clubName) {
    this.clubName = clubName;
  }

  public String getInstagramUrl() {
    return instagramUrl;
  }

  public void setInstagramUrl(String instagramUrl) {
    this.instagramUrl = instagramUrl;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public boolean isNotifyViaInstagram() {
    return notifyViaInstagram;
  }

  public void setNotifyViaInstagram(boolean notifyViaInstagram) {
    this.notifyViaInstagram = notifyViaInstagram;
  }

  public boolean isNotifyViaEmail() {
    return notifyViaEmail;
  }

  public void setNotifyViaEmail(boolean notifyViaEmail) {
    this.notifyViaEmail = notifyViaEmail;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public RequestStatus getStatus() {
    return status;
  }

  public void setStatus(RequestStatus status) {
    this.status = status;
  }

  public Long getIsProcessedBy() {
    return isProcessedBy;
  }

  public void setIsProcessedBy(Long isProcessedBy) {
    this.isProcessedBy = isProcessedBy;
  }
}
