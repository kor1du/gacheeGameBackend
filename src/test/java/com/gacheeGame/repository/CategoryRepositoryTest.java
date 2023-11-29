package com.gacheeGame.repository;

import com.gacheeGame.configuration.QueryDslConfig;
import com.gacheeGame.entity.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({QueryDslConfig.class})
public class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;

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

        //when
        List<Category> dbCategoryList = categoryRepository.findAll();

        //then
        assertThat(dbCategoryList)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(categoryList);
    }
}
