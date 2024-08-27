package com.zonainmueble.surveys.models;

import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "survey_responses_http_info")
public class SurveyResponseHttpInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private Long surveyResposeId;

  private String ip;
  private String browser;
  private String referer;
  private String language;
  private String accept;

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
}
