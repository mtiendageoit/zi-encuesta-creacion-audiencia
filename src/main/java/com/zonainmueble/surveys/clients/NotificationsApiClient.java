package com.zonainmueble.surveys.clients;

import java.util.List;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.zonainmueble.surveys.config.AppConfig;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class NotificationsApiClient {
  private final AppConfig config;
  private final RestTemplate restTemplate;

  public void sendNotifications(List<Notification> notifications) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<List<Notification>> requestEntity = new HttpEntity<>(notifications, headers);

    ResponseEntity<Void> response = restTemplate.exchange(config.getNotificationsApiUrl(), HttpMethod.POST,
        requestEntity, Void.class);

    if (response.getStatusCode() != HttpStatus.OK) {
      log.info("notifications: {}", notifications);
      throw new RuntimeException("Failed to send notifications");
    }
  }
}
