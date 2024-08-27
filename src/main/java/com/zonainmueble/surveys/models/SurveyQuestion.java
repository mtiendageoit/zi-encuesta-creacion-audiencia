package com.zonainmueble.surveys.models;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.zonainmueble.surveys.enums.QuestionType;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "survey_questions")
public class SurveyQuestion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  private String question;

  @NotNull
  @Enumerated(EnumType.STRING)
  private QuestionType type;

  @JsonManagedReference
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
  private List<QuestionOption> options = new ArrayList<>();
}