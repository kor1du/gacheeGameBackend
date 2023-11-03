package com.gacheeGame.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CategoryDto 
{
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response
    {
        private Long categoryId; //PK
        private String name; //카테고리 이름
    }
}
