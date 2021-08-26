package com.baizhi.serviceimpl;

import com.baizhi.dao.CategoryMapper;
import com.baizhi.dto.CategoryPageDTO;
import com.baizhi.dto.PageDTO;
import com.baizhi.entity.Category;
import com.baizhi.entity.CategoryExample;
import com.baizhi.service.CategoryService;
import com.baizhi.util.UUIDUtil;
import com.baizhi.vo.CommonQueryPageVO;
import com.baizhi.vo.CommonVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;
    @Override
    public CommonQueryPageVO queryOnePage(PageDTO pageDTO) {
        //创建条件对象
        CategoryExample example = new CategoryExample();
        //example.createCriteria().andParentIdIsNull();
        example.createCriteria().andLevelsEqualTo(1); //levels=1

        //根据条件查一级类别的数量  条件  levels=1
        int total = categoryMapper.selectCountByExample(example);

        RowBounds rowBounds = new RowBounds((pageDTO.getPage() - 1) * pageDTO.getPageSize(), pageDTO.getPageSize());

        //分页查询数据
        List<Category> categoryList = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);

       /* CommonQueryPageVO commonQueryPageVO = new CommonQueryPageVO();
        commonQueryPageVO.setPage(pageDTO.getPage());
        commonQueryPageVO.setTotal(total);
        commonQueryPageVO.setRows(categoryList);*/

        return new CommonQueryPageVO(pageDTO.getPage(),total,categoryList);
    }

    @Override
    public CommonVo add(Category category) {

        try {
            //判断添加的是一级类别还是二级类别
            if(category.getParentId()!=null){
                //二级类别
                category.setLevels(2);
            }else {
                //一级类别
                category.setLevels(1);
            }
            category.setId(UUIDUtil.getUUID());
            //添加数据
            categoryMapper.insertSelective(category);
            return CommonVo.success("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonVo.faild("添加失败");
        }
    }

    @Override
    public CommonVo delete(Category category) {
        //判断删除的是一级类别还是二级类别
        if(category.getParentId()==null){
            //一级类别  判断一级类别下是否有二级类别
            //根据一级类别ID查询该一级类别下是否有二级类别
            CategoryExample categoryExample = new CategoryExample();
            categoryExample.createCriteria().andParentIdEqualTo(category.getId());
            int count = categoryMapper.selectCountByExample(categoryExample);
            if(count==0){
                //没有直接删除
                categoryMapper.delete(category);
                return CommonVo.success("一级类别删除成功");
            }else {
                //有不能删除
                return CommonVo.faild("该一级类别下存在二级类别不能删除");
            }
        }else {
            //二级类别，判断该二级类别下是否有视频
            //有     不能删除
            //没有    直接删除
            return CommonVo.faild("二级类别删除成功");
        }
    }

    @Override
    public Category queryById(String id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public CommonVo update(Category category) {

        try {
            categoryMapper.updateByPrimaryKeySelective(category);
            return CommonVo.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonVo.faild("删除失败");
        }
    }

    @Override
    public CommonQueryPageVO queryTwoPage(CategoryPageDTO categoryPageDTO) {
        //创建条件对象
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andParentIdEqualTo(categoryPageDTO.getCategoryId());
        //根据条件查询一级类别的数据 条件 levels=1
        int total = categoryMapper.selectCountByExample(categoryExample);

        RowBounds rowBounds = new RowBounds((categoryPageDTO.getPage() - 1) * categoryPageDTO.getPageSize(), categoryPageDTO.getPageSize());

        //分页查询数据
        List<Category> categoryList = categoryMapper.selectByExampleAndRowBounds(categoryExample, rowBounds);

        return new CommonQueryPageVO(categoryPageDTO.getPage(),total,categoryList);
    }

    @Override
    public List<Category> queryByLevelsCategory(Integer levels) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andLevelsEqualTo(levels);

        List<Category> categories = categoryMapper.selectByExample(categoryExample);

        return categories;
    }
}
