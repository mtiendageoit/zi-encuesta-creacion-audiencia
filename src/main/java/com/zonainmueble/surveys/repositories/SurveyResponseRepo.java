package com.zonainmueble.surveys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zonainmueble.surveys.models.SurveyResponse;

public interface SurveyResponseRepo extends JpaRepository<SurveyResponse,Long> {
  boolean existsByEmail(String email);
}
