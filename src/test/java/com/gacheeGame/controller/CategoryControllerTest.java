package com.gacheeGame.controller;

import com.gacheeGame.dto.CategoryDto;
import com.gacheeGame.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
    private final String URL = "/category/categoryList";

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    @DisplayName("[200] 카테고리 전체 조회")
    public void OK_카테고리_전체_조회() throws Exception {
        //given
        List<CategoryDto.Response> categoryList = new ArrayList<>();

        categoryList.add(CategoryDto.Response
                .builder()
                .categoryId(1L)
                .name("연애")
                .build());

        categoryList.add(CategoryDto.Response
                .builder()
                .categoryId(2L)
                .name("결혼")
                .build());

        when(categoryService.categoryList()).thenReturn(categoryList);

        //when
        ResultActions resultAction = mockMvc.perform(
                MockMvcRequestBuilders.get(URL));

        //then
        resultAction
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("카테고리를 정상적으로 불러왔습니다."));

        // JSON 배열 내의 항목을 검사하는 반복문
        for (int i = 0; i < categoryList.size(); i++) {
            String jsonPathPrefix = String.format("$.body.categoryList[%d]", i);
            resultAction
                    .andExpect(jsonPath(jsonPathPrefix + ".categoryId").value(categoryList.get(i).getCategoryId()))
                    .andExpect(jsonPath(jsonPathPrefix + ".name").value(categoryList.get(i).getName()));
        }
    }
}
