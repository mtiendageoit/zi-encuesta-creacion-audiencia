package com.zonainmueble.surveys.tasks;

import java.util.*;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zonainmueble.surveys.clients.*;
import com.zonainmueble.surveys.enums.*;
import com.zonainmueble.surveys.models.*;
import com.zonainmueble.surveys.repositories.*;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class ReportNotificationTask {
  private final ReportsApiClient reportsApiClient;
  private final NotificationsApiClient notificationsApiClient;

  private final SurveyReportRepo surveyReportRepo;
  private final SurveyResponseRepo surveyResponseRepo;

  @Scheduled(fixedDelayString = "#{@appConfig.getReportProcessTaskDelay()}")
  public void executeTask() {
    for (SurveyResponse item : surveyResponseRepo.findAllToSendBasicReport()) {
      log.info("Processing survey response: {}", item);

      sendReportToSurveyResponse(item);

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        log.error("Thread interrupted while waiting to send next report", e);
      }
    }
  }

  private void sendReportToSurveyResponse(SurveyResponse survey) {
    byte[] reportFile = null;
    SurveyReportStatus status = SurveyReportStatus.SUCCESS;

    try {
      ReportRequestDto reportRequest = reportRequest(survey);
      reportFile = reportsApiClient.report(ReportType.GRATUITO, reportRequest);
    } catch (Exception e) {
      log.error("Error while generating report", e);
      log.error("survey: {}", survey);

      status = SurveyReportStatus.REPORT_ERROR;
      registerSurveyReport(survey, status, e.getMessage());
      throw e;
    }

    try {
      ReporteGratisNotification notification = reporteGratisNotification(survey, reportFile);
      notificationsApiClient.sendReporteGratisNotification(notification);
    } catch (Exception e) {
      log.error("Error sending notification", e);
      log.error("survey: {}", survey);

      status = SurveyReportStatus.NOTIFICATION_ERROR;
      registerSurveyReport(survey, status, e.getMessage());
      throw e;
    }

    registerSurveyReport(survey, status, null);
  }

  private void registerSurveyReport(SurveyResponse survey, SurveyReportStatus status, String error) {
    SurveyReport item = SurveyReport.builder()
        .surveyResposeId(survey.getId())
        .reportType(ReportType.GRATUITO)
        .status(status)
        .error(error)
        .createdAt(new Date())
        .build();

    surveyReportRepo.save(item);
  }

  private ReportRequestDto reportRequest(SurveyResponse survey) {
    return ReportRequestDto.builder()
        .address(survey.getAddress())
        .longitude(survey.getLongitude())
        .latitude(survey.getLatitude())
        .build();
  }

  private ReporteGratisNotification reporteGratisNotification(SurveyResponse survey, byte[] file) {
    ReporteGratisNotification notification = new ReporteGratisNotification();
    notification.setEmail(survey.getEmail());
    notification.setPhone(survey.getPhone());
    notification.setAddress(survey.getAddress());
    notification.setPdf(new AttachFile("Zonainmueble-reporte-basico-ubicacion.pdf", file));

    return notification;
  }

}
