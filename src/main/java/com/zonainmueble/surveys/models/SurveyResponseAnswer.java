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
@Table(name = "survey_response_answers")
public class SurveyResponseAnswer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private Long surveyResposeId;

  @NotNull
  private Integer questionId;

  @NotNull
  private Integer optionId;

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
}
