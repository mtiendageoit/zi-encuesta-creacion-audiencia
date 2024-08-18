package com.zonainmueble.surveys.dtos;

import lombok.*;

@Getter
@Builder
public class HttpClientRequestInfoDto {
  private String ip;
  private String browser;
  private String referer;
  private String language;
  private String accept;
}
