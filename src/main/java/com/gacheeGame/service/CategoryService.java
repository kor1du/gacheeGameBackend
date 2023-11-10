package com.gacheeGame.service;

import com.gacheeGame.dto.CategoryDto;
import com.gacheeGame.dto.CategoryDto.Response;
import com.gacheeGame.dto.ResponseDto;
import com.gacheeGame.entity.Category;
import com.gacheeGame.handler.CustomBadRequestException;
import com.gacheeGame.mapper.CategoryMapper;
import com.gacheeGame.repository.CategoryRepository;
import com.gacheeGame.util.JsonUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService
{
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    //카테고리 리스트 반환
    @Transactional(readOnly = true)
    public List<Response> categoryList()
    {
        try {
            List<Response> categoryList = categoryMapper.map(categoryRepository.findAll());

            return categoryList;
        }catch (Exception e){
            log.error("카테고리를 불러오던 중 오류가 발생하였습니다.", e);
            throw new CustomBadRequestException("카테고리를 불러오던 중 오류가 발생하였습니다.");
        }
    }
}
