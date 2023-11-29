package com.gacheeGame.service;

import com.gacheeGame.dto.CategoryDto.Response;
import com.gacheeGame.entity.Category;
import com.gacheeGame.handler.CustomBadRequestException;
import com.gacheeGame.mapper.CategoryMapper;
import com.gacheeGame.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    //카테고리 리스트 반환
    @Transactional(readOnly = true)
    public List<Response> categoryList() {
        try {
            List<Category> dbCategoryList = categoryRepository.findAll(); //DB에서 가져온 카테고리 목록

            if (dbCategoryList == null || dbCategoryList.isEmpty()) {
                throw new CustomBadRequestException("카테고리 목록이 존재하지 않습니다!");
            }

            List<Response> categoryListDto = categoryMapper.map(dbCategoryList); //Mapping(DB에서 가져온 카테고리 -> DTO)

            return categoryListDto;
        } catch (CustomBadRequestException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("카테고리를 불러오던 중 오류가 발생하였습니다.", e);
            throw new CustomBadRequestException("카테고리를 불러오던 중 오류가 발생하였습니다.");
        }
    }
}
