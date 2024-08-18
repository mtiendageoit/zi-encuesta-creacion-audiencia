package com.zonainmueble.surveys.dtos;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SurveyResponseDto {
  @Email
  @NotBlank
  private String email;

  @Pattern(regexp = "^\\d{10}$", message = "The phone number must contain exactly 10 digits")
  private String phone;

  @NotBlank
  @Size(min = 10, max = 255, message = "The address must be between 10 and 255 characters long")
  private String address;

  @Valid
  @NotEmpty
  private List<QuestionAnswersDto> answers;
}
