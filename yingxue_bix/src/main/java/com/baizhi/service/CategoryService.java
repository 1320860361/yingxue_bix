package com.baizhi.service;

import com.baizhi.dto.CategoryPageDTO;
import com.baizhi.dto.PageDTO;
import com.baizhi.entity.Category;
import com.baizhi.vo.CommonQueryPageVO;
import com.baizhi.vo.CommonVo;

import java.util.List;

public interface CategoryService {

    CommonQueryPageVO queryOnePage(PageDTO pageDTO);

    CommonVo add(Category category);

    CommonVo deletes(Category category);
    String delete(Category category);

    Category queryById(String id);

    CommonVo update(Category category);

    CommonQueryPageVO queryTwoPage(CategoryPageDTO categoryPageDTO);

    List<Category> queryByLevelsCategory(Integer levels);

}
