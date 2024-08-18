package com.zonainmueble.surveys.utils;

import com.zonainmueble.surveys.dtos.HttpClientRequestInfoDto;

import jakarta.servlet.http.HttpServletRequest;

public class HttpRequestUtils {

  public static HttpClientRequestInfoDto getClientInfo(HttpServletRequest request) {
    return HttpClientRequestInfoDto.builder()
        .ip(getIP(request))
        .browser(getBrowser(request))
        .referer(getReferer(request))
        .language(getLanguage(request))
        .accept(getAcept(request))
        .build();
  }

  private static String getIP(HttpServletRequest request) {
    final String xfHeader = request.getHeader("X-Forwarded-For");
    if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
      return request.getRemoteAddr();
    }
    return xfHeader.split(",")[0];
  }

  private static String getBrowser(HttpServletRequest request) {
    return request.getHeader("User-Agent");
  }

  private static String getReferer(HttpServletRequest request) {
    return request.getHeader("Referer");
  }

  private static String getLanguage(HttpServletRequest request) {
    return request.getHeader("Accept-Language");
  }

  private static String getAcept(HttpServletRequest request) {
    return request.getHeader("Accept");
  }
}
