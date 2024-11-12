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
@Table(name = "survey_responses")
public class SurveyResponse {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String email;
  private String phone;

  @NotNull
  private String address;

  @NotNull
  private Double longitude;

  @NotNull
  private Double latitude;

  private String source;

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
}
