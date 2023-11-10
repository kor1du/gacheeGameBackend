package com.gacheeGame.controller;

import com.gacheeGame.dto.CategoryDto;
import com.gacheeGame.dto.CategoryDto.Response;
import com.gacheeGame.dto.ResponseDto;
import com.gacheeGame.service.CategoryService;
import com.gacheeGame.util.JsonUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    //카테고리 리스트 반환
    @GetMapping("/categoryList")
    public ResponseEntity<ResponseDto> categoryList()
    {
        List<Response> categoryList = categoryService.categoryList();

        ResponseDto responseDto = ResponseDto
                                            .builder()
                                            .status(HttpStatus.OK.value())
                                            .body(JsonUtil.ObjectToJsonObject("categoryList", categoryList))
                                            .message("카테고리를 정상적으로 불러왔습니다.")
                                            .build();

        return ResponseEntity.ok(responseDto);
    }
}
