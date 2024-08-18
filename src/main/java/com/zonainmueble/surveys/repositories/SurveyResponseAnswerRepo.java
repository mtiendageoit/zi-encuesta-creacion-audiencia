package com.zonainmueble.surveys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zonainmueble.surveys.models.SurveyResponseAnswer;

public interface SurveyResponseAnswerRepo extends JpaRepository<SurveyResponseAnswer, Long> {
  
}
