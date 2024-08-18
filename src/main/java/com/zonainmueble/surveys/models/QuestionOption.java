package com.zonainmueble.surveys.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "question_options")
public class QuestionOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "question_id", nullable = false)
    private SurveyQuestion question;

    @Column(nullable = false)
    private String option;
}