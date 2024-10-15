package com.zonainmueble.surveys.services;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.zonainmueble.surveys.dtos.*;
import com.zonainmueble.surveys.exceptions.AlreadyRegisterException;
import com.zonainmueble.surveys.models.*;
import com.zonainmueble.surveys.repositories.*;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SurveyService {
  private final SurveyQuestionRepo surveyQuestionRepo;
  private final SurveyResponseRepo surveyResponseRepo;
  private final SurveyResponseAnswerRepo surveyResponseAnswerRepo;
  private final SurveyResponseHttpInfoRepo surveyResponseHttpInfoRepo;
  private final SurveyResponseAnswerOtherRepo surveyResponseAnswerOtherRepo;

  public List<SurveyQuestion> survey() {
    return surveyQuestionRepo.findByIdWithOptions();
  }

  @Transactional
  public void registerSurvey(SurveyResponseDto input, HttpClientRequestInfoDto info) {
    SurveyResponse response = saveResponse(input);
    saveResponseAnswers(input, response.getId());
    saveRequestInfo(info, response.getId());
  }

  private void saveRequestInfo(HttpClientRequestInfoDto info, long surveyResposeId) {
    surveyResponseHttpInfoRepo.save(SurveyResponseHttpInfo.builder()
        .surveyResposeId(surveyResposeId)
        .ip(info.getIp())
        .browser(info.getBrowser())
        .referer(info.getReferer())
        .language(info.getLanguage())
        .accept(info.getAccept())
        .createdAt(new Date())
        .build());
  }

  private SurveyResponse saveResponse(SurveyResponseDto input) {
    validateUniqueSurveyResponse(input);

    SurveyResponse response = SurveyResponse.builder()
        .email(input.getEmail())
        .phone(input.getPhone())
        .address(input.getAddress())
        .latitude(input.getLatitude())
        .longitude(input.getLongitude())
        .createdAt(new Date())
        .build();

    return surveyResponseRepo.save(response);
  }

  private void validateUniqueSurveyResponse(SurveyResponseDto input) {
    boolean exists = surveyResponseRepo.existsByEmail(input.getEmail());
    if (exists) {
      throw new AlreadyRegisterException("email-already-register", "Email is already register");
    }

    if (StringUtils.hasText(input.getPhone())) {
      exists = surveyResponseRepo.existsByPhone(input.getPhone());
      if (exists) {
        throw new AlreadyRegisterException("phone-already-register", "Phone is already register");
      }
    }
  }

  private void saveResponseAnswers(SurveyResponseDto input, long surveyResposeId) {
    List<SurveyQuestion> questions = survey();

    QuestionAnswersDto answersDto;
    Optional<QuestionOption> option;
    List<SurveyResponseAnswer> answers = new ArrayList<>();
    List<SurveyResponseAnswerOther> answersOthers = new ArrayList<>();

    for (SurveyQuestion question : questions) {
      answersDto = input.getAnswers().stream().filter(a -> a.getQuestionId().equals(question.getId())).findAny()
          .orElseThrow();

      if (answersDto.getOptionIds() != null) {
        for (Integer optionId : answersDto.getOptionIds()) {
          option = question.getOptions().stream().filter(o -> o.getId().equals(optionId)).findAny();
          if (option.isEmpty()) {
            continue;
          }
          answers.add(SurveyResponseAnswer.builder()
              .surveyResposeId(surveyResposeId)
              .questionId(question.getId())
              .optionId(optionId)
              .createdAt(new Date())
              .build());
        }
      }

      if (answersDto.getOther() != null && !answersDto.getOther().isBlank()) {
        answersOthers.add(SurveyResponseAnswerOther.builder()
            .surveyResposeId(surveyResposeId)
            .questionId(question.getId())
            .other(answersDto.getOther())
            .createdAt(new Date())
            .build());
      }
    }

    if (!answers.isEmpty()) {
      surveyResponseAnswerRepo.saveAll(answers);
    }

    if (!answersOthers.isEmpty()) {
      surveyResponseAnswerOtherRepo.saveAll(answersOthers);
    }
  }

}
