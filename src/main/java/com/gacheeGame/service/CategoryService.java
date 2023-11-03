package com.gacheeGame.service;

import com.gacheeGame.dto.CategoryDto;
import com.gacheeGame.dto.ResponseDto;
import com.gacheeGame.handler.CustomBadRequestException;
import com.gacheeGame.repository.CategoryRepository;
import com.gacheeGame.util.JsonUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService
{
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public ResponseDto categoryList()
    {
        try {
            List<CategoryDto.Response> categoryList = categoryRepository
                                                                        .findAll()
                                                                        .stream()
                                                                        .map(c -> modelMapper.map(c, CategoryDto.Response.class))
                                                                        .collect(Collectors.toList());

            return ResponseDto
                            .builder()
                            .status(HttpStatus.OK.value())
                            .body(JsonUtil.ObjectToJsonObject("categoryList", categoryList))
                            .message("카테고리를 정상적으로 불러왔습니다.")
                            .build();
        }catch (Exception e){
            log.error("카테고리를 불러오던 중 오류가 발생하였습니다.");
            throw new CustomBadRequestException("카테고리를 불러오던 중 오류가 발생하였습니다.");
        }
    }
}
