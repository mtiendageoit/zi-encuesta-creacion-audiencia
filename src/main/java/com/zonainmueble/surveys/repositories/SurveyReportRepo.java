package com.zonainmueble.surveys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zonainmueble.surveys.models.SurveyReport;

public interface SurveyReportRepo extends JpaRepository<SurveyReport, Long> {

}
