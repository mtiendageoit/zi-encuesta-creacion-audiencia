package com.zonainmueble.surveys.dtos;

import java.util.List;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class QuestionAnswersDto {
  @NotNull
  private Integer questionId;
  
  private List<Integer> optionIds;
  private String other;
}
