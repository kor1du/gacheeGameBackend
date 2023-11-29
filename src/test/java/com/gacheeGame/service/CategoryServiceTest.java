package com.gacheeGame.service;

import com.gacheeGame.dto.CategoryDto.Response;
import com.gacheeGame.entity.Category;
import com.gacheeGame.handler.CustomBadRequestException;
import com.gacheeGame.mapper.CategoryMapper;
import com.gacheeGame.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Test
    @DisplayName("OK 카테고리 전체 조회")
    public void OK_카테고리_전체_조회() {
        //given
        List<Category> categoryList = new ArrayList<>();

        categoryList.add(Category
                .builder()
                .categoryId(1L)
                .name("연애")
                .build());

        categoryList.add(Category
                .builder()
                .categoryId(2L)
                .name("결혼")
                .build());

        List<Response> categoryDtoResponseList = new ArrayList<>();

        categoryDtoResponseList.add(Response
                .builder()
                .categoryId(1L)
                .name("연애")
                .build());

        categoryDtoResponseList.add(Response
                .builder()
                .categoryId(2L)
                .name("결혼")
                .build());

        doReturn(categoryList).when(categoryRepository).findAll();
        doReturn(categoryDtoResponseList).when(categoryMapper).map(categoryList);

        //when
        List<Response> result = categoryService.categoryList();

        //then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(categoryDtoResponseList);
    }

    @Test
    @DisplayName("BAD REQUEST 카테고리 전체 조회")
    public void BAD_REQUEST_카테고리_전체_조회() {
        //given
        List<Category> categoryList = new ArrayList<>();

        doReturn(categoryList).when(categoryRepository).findAll();

        //when
        CustomBadRequestException exception = assertThrows(CustomBadRequestException.class, () -> {
            categoryService.categoryList();
        });

        //then
        assertEquals("카테고리 목록이 존재하지 않습니다!", exception.getMessage());
    }
}
