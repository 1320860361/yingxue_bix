package com.baizhi;

import com.baizhi.dao.CategoryMapper;
import com.baizhi.dao.FeedbackMapper;
import com.baizhi.entity.Category;
import com.baizhi.entity.CategoryExample;
import com.baizhi.entity.Feedback;
import com.baizhi.entity.FeedbackExample;
import com.baizhi.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class YingxueBixApplicationTests {

    @Resource
    CategoryMapper categoryMapper;
    @Test
    void contextLoads() {
        Category category = new Category();
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andIdEqualTo("37");
        //category.setId("37");
        Category category1 = categoryMapper.selectOneByExample(categoryExample);
        System.out.println(category1);
        }


    @Test
    void contextLoadss() {
        Category category = categoryMapper.queryAllByIdS("37");
        System.out.println(category);
    }

}

