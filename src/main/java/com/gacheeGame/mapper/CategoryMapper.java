package com.gacheeGame.mapper;

import com.gacheeGame.dto.CategoryDto;
import com.gacheeGame.dto.CategoryDto.Response;
import com.gacheeGame.entity.Category;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper
{
    List<Response> map (List<Category> categoryList);
}
