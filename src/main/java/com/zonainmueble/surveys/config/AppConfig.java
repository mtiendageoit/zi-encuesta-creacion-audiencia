package com.zonainmueble.surveys.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration
public class AppConfig {

  @Value("${app.apis.notifications.url}")
  private String notificationsApiUrl;

  @Value("${report.process.task.delay:300000}") // 5 Minutes
  private long reportProcessTaskDelay;
}
