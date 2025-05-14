package de.alixcja.clubhilfe.requestserivce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Server {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String serverName;

  private int serverNumber;

  private boolean isArchived = false;

  public Server() {
  }

  public Server(String serverName, int serverNumber) {
    this.serverName = serverName;
    this.serverNumber = serverNumber;
  }

  public Server(Long id, String serverName, int serverNumber) {
    this.id = id;
    this.serverName = serverName;
    this.serverNumber = serverNumber;
  }

  public Long getId() {
    return id;
  }

  public String getServerName() {
    return serverName;
  }

  public void setServerName(String serverName) {
    this.serverName = serverName;
  }

  public int getServerNumber() {
    return serverNumber;
  }

  public void setServerNumber(int serverNumber) {
    this.serverNumber = serverNumber;
  }

  public boolean getIsArchived() {
    return isArchived;
  }

  public void setIsArchived(boolean archived) {
    isArchived = archived;
  }
}
