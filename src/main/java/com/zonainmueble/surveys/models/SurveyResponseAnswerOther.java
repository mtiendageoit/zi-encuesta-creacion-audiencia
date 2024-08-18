package com.zonainmueble.surveys.models;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "survey_response_answer_others")
public class SurveyResponseAnswerOther {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long surveyResposeId;
  private Integer questionId;
  private String other;

  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
}
