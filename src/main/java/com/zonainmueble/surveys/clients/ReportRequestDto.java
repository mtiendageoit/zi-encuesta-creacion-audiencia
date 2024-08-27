package com.zonainmueble.surveys.clients;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequestDto {
  @NotBlank
  private String address;

  @NotBlank
  private double longitude;

  @NotBlank
  private double latitude;
}
