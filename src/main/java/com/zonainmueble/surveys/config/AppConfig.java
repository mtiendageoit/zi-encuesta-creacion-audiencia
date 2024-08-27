package com.zonainmueble.surveys.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration
public class AppConfig {

  @Value("${api.notifications.url}")
  private String notificationsApiUrl;

  @Value("${api.reports.url}")
  private String reportsApiUrl;
}
