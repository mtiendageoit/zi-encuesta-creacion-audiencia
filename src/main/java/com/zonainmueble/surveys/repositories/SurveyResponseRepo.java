package com.zonainmueble.surveys.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zonainmueble.surveys.models.SurveyResponse;

public interface SurveyResponseRepo extends JpaRepository<SurveyResponse, Long> {
  boolean existsByEmail(String email);

  @Query(value = "SELECT * FROM basic_reports_to_send", nativeQuery = true)
  List<SurveyResponse> findAllToSendBasicReport();
}
