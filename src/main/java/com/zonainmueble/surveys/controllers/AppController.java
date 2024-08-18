package com.zonainmueble.surveys.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Controller
@AllArgsConstructor
@RequestMapping("/")
public class AppController {

  @GetMapping
  public String survey() {
    return "survey";
  }
}
