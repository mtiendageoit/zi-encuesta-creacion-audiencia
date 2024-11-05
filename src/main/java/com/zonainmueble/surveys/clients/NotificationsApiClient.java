package com.zonainmueble.surveys.clients;

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

  public void sendReporteGratisNotification(ReporteGratisNotification notification) {

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<ReporteGratisNotification> requestEntity = new HttpEntity<>(notification, headers);

    try {
      String url = notificationsApiUrl + "/reporte-gratis";
      ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST,
          requestEntity, Void.class);

      if (response.getStatusCode() != HttpStatus.OK) {
        throw new RuntimeException("Failed to send notification");
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      log.error("Failed to send notification: {}, url: {}", notification, notificationsApiUrl);
      throw new BaseException("FAILED_TO_SEND_NOTIFICATION", "Failed to send notification");
    }
  }
}
