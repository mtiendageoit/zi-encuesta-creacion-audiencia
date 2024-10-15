package com.zonainmueble.surveys.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.zonainmueble.surveys.enums.ReportType;
import com.zonainmueble.surveys.exceptions.BaseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportsApiClient {
  private final RestTemplate restTemplate;

  @Value("${app.apis.reports-url}")
  private String reportsApiUrl;

  @Value("${app.apis.reports-api-key}")
  private String reportsApiKey;

  public byte[] report(ReportType type, ReportRequestDto input) {
    String url = reportsApiUrl + "?type=" + type.name() + "&api_key=" + reportsApiKey;

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<ReportRequestDto> requestEntity = new HttpEntity<>(input, headers);

    try {
      ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, byte[].class);

      if (response.getStatusCode() == HttpStatus.OK) {
        return response.getBody();
      } else {
        log.error("Failed to get PDF from input: {}, type: {}, url: {}, responseStatusCode: {}", input, type, url,
            response.getStatusCode());
        throw new BaseException("FAILED_TO_GENERATE_REPORT", "Failed to generate report");
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      log.error("Failed to get PDF from input: {}, type: {}, url: {}", input, type, url);
      throw new BaseException("FAILED_TO_GENERATE_REPORT", "Failed to generate report");
    }
  }
}
