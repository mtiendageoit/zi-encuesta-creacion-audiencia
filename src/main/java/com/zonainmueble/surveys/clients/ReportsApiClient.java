package com.zonainmueble.surveys.clients;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.zonainmueble.surveys.config.AppConfig;
import com.zonainmueble.surveys.enums.ReportType;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ReportsApiClient {
  private final AppConfig config;
  private final RestTemplate restTemplate;

  public byte[] report(ReportType type, ReportRequestDto input) {
    String url = config.getReportsApiUrl() + "?type=" + type.name();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<ReportRequestDto> requestEntity = new HttpEntity<>(input, headers);

    ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, byte[].class);

    if (response.getStatusCode() == HttpStatus.OK) {
      return response.getBody();
    } else {
      throw new RuntimeException("Failed to download PDF");
    }
  }
}
