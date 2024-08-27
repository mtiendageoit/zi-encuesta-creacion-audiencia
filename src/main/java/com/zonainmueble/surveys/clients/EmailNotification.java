package com.zonainmueble.surveys.clients;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper=false)
public class EmailNotification extends Notification {
  public EmailNotification() {
    super(NotificationType.EMAIL);
  }

  private String fromName;

  @Email
  @NotBlank
  private String to;

  @NotBlank
  private String subject;

  @NotBlank
  private String body;
  private boolean html;

  @Valid
  private List<EmailAttachFile> attachFiles;

  private List<String> cc;
  private List<String> bcc;
}
