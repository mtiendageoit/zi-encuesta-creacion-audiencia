package com.zonainmueble.surveys.models;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "survey_response_answers")
public class SurveyResponseAnswer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long surveyResposeId;

  private Integer questionId;
  private Integer optionId;

  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
}
