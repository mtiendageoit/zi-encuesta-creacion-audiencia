package com.zonainmueble.surveys.clients;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReporteGratisNotification {
  @Email
  @NotBlank
  private String email;
  
  private String phone;

  @NotBlank
  private String address;

  @Valid
  private AttachFile pdf;
}
