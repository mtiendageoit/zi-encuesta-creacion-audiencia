package com.zonainmueble.surveys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zonainmueble.surveys.models.SurveyResponseHttpInfo;

public interface SurveyResponseHttpInfoRepo extends JpaRepository<SurveyResponseHttpInfo, Long> {
  
}
