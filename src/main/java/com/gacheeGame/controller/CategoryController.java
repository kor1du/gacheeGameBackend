package com.gacheeGame.controller;

import com.gacheeGame.dto.ResponseDto;
import com.gacheeGame.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController
{
    private final CategoryService categoryService;

    @GetMapping("/categoryList")
    public ResponseEntity<ResponseDto> categoryList()
    {
        return ResponseEntity.ok(categoryService.categoryList());
    }
}
