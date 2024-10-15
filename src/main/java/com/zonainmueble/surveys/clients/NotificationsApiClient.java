package com.zonainmueble.surveys.clients;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.zonainmueble.surveys.exceptions.BaseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationsApiClient {
  private final RestTemplate restTemplate;

  @Value("${app.apis.notifications.url}")
  private String notificationsApiUrl;

  public void sendNotifications(List<Notification> notifications) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<List<Notification>> requestEntity = new HttpEntity<>(notifications, headers);

    try {
      ResponseEntity<Void> response = restTemplate.exchange(notificationsApiUrl, HttpMethod.POST,
          requestEntity, Void.class);

      if (response.getStatusCode() != HttpStatus.OK) {
        throw new RuntimeException("Failed to send notifications");
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      log.error("Failed to send notifications: {}, url: {}", notifications, notificationsApiUrl);
      throw new BaseException("FAILED_TO_SEND_NOTIFICATIONS", "Failed to send notifications");
    }
  }
}
