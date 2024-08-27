package com.zonainmueble.surveys.clients;

import lombok.Data;

@Data
public abstract class Notification {
  private NotificationType type;

  public Notification(NotificationType type) {
    this.type = type;
  }
}
