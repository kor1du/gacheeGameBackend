package com.gacheeGame.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Question
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false)
    private Long questionId; //PK

    @Column(name = "situation", nullable = false)
    private String situation; //상황 설명

    @Column(name = "situation_image", nullable = false)
    private String situationImage; //상황 이미지

    @Column(name = "title", nullable = false)
    private String title; //상황 제목

    @Column(name = "title_image", nullable = false)
    private String titleImage; //상황 제목 이미지

    @Column(name = "sub_title", nullable = false)
    private String subTitle; //상황 서브제목

    @JsonIgnore
    @Column(name = "category_id", nullable = false)
    private Long categoryId; //FK (카테고리)
}
