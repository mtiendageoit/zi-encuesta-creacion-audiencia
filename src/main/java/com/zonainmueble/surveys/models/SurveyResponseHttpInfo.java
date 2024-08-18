package com.zonainmueble.surveys.models;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "survey_responses_http_info")
public class SurveyResponseHttpInfo {
  @Id
  private Long surveyResposeId;

  private String ip;
  private String browser;
  private String referer;
  private String language;
  private String accept;

   @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
}
