package com.baizhi.serviceimpl;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.FeedbackMapper;
import com.baizhi.dao.UserMapper;
import com.baizhi.entity.Feedback;
import com.baizhi.entity.FeedbackExample;
import com.baizhi.entity.User;
import com.baizhi.service.FeedbackService;
import com.baizhi.service.UserService;
import com.baizhi.vo.CommonVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;
    @Override
    public HashMap<String, Object> queryAllPage(Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        //设置当前页
        map.put("page",page);
        //设置条件查询对象
        FeedbackExample example = new FeedbackExample();
        //查询总数
        int count = userMapper.selectCountByExample(example);

        //设置总条数
        map.put("total",count);

        //创建的分页对象，参数，起始条数、数据数
        RowBounds rowBounds = new RowBounds((page - 1)*pageSize, pageSize);
        List<User> feedbacks = userMapper.selectByExampleAndRowBounds(example, rowBounds);

        //根据分页查询数据
        map.put("rows",feedbacks);
        return map;
    }

    @Override
    public HashMap<String, Object> update(User user) {
        return null;
    }

    /* @Override
     public HashMap<String, Object> update(User user) {
          HashMap<String, Object> map = new HashMap<>();
        try {
             userMapper.updateByPrimaryKeySelective(user);
            return CommonVo.success("修改成功");
         } catch (Exception e) {
             e.printStackTrace();
             return CommonVo.faild("修改失败");
         }
         *//*try {
            userMapper.updateByPrimaryKeySelective(user);
            return CommonVO.success("message");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonVO.faild();
       }*//*
        //return map;
    }*/
   @AddLog(value = "修改用户")
   @Override
   public CommonVo updates(User user) {
       try {
           userMapper.updateByPrimaryKeySelective(user);
           return CommonVo.success("修改成功");
       } catch (Exception e) {
           e.printStackTrace();
           return CommonVo.faild("修改失败");
       }
        /*try {
            userMapper.updateByPrimaryKeySelective(user);
            return CommonVO.success("message");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonVO.faild();
       }*/
       //return map;
   }

}
