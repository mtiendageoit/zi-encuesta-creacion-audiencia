package com.zonainmueble.surveys.models;

import java.util.Date;

import com.zonainmueble.surveys.enums.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Entity
@Builder
@Table(name = "survey_reports")
public class SurveyReport {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private Long surveyResposeId;

  @NotNull
  @Enumerated(EnumType.STRING)
  private ReportType reportType;

  @NotNull
  @Enumerated(EnumType.STRING)
  private SurveyReportStatus status;

  private String error;

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
}
