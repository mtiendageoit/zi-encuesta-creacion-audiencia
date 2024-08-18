package com.zonainmueble.surveys.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zonainmueble.surveys.models.SurveyQuestion;

public interface SurveyQuestionRepo extends JpaRepository<SurveyQuestion, Integer> {
  @Query("SELECT q FROM SurveyQuestion q JOIN FETCH q.options")
  List<SurveyQuestion> findByIdWithOptions();
}
