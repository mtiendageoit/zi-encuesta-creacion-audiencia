package com.zonainmueble.surveys.models;

import java.util.Date;

import jakarta.persistence.*;
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

  private String email;
  private String phone;

  private String address;
  // private String longitude;
  // private String latitude;

  // private boolean acceptedPrivacyPolicy;
  // private boolean acceptedReceiveInfo;

  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
}
