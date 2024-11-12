package com.zonainmueble.surveys.api;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.zonainmueble.surveys.dtos.HttpClientRequestInfoDto;
import com.zonainmueble.surveys.dtos.SurveyResponseDto;
import com.zonainmueble.surveys.models.SurveyQuestion;
import com.zonainmueble.surveys.services.SurveyService;
import com.zonainmueble.surveys.utils.HttpRequestUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/encuestas/creacion-audiencia")
public class ApiController {
  private final SurveyService surveyService;

  @GetMapping
  public List<SurveyQuestion> survey() {
    return surveyService.survey();
  }

  @PostMapping
  public void registerSurvey(@Valid @RequestBody SurveyResponseDto input, HttpServletRequest request) {
    log.info("Pocessing input: {}", input);
    HttpClientRequestInfoDto info = HttpRequestUtils.getClientInfo(request);
    surveyService.registerSurvey(input, info);
  }
}
