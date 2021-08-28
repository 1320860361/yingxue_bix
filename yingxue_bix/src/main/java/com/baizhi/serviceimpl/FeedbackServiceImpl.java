package com.baizhi.serviceimpl;

import com.baizhi.annotation.AddLog;
import com.baizhi.dao.FeedbackMapper;
import com.baizhi.entity.Feedback;
import com.baizhi.entity.FeedbackExample;
import com.baizhi.service.FeedbackService;
import org.apache.ibatis.session.RowBounds;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {


    @Resource
    FeedbackMapper feedbackMapper;
    @Override
    public HashMap<String, Object> queryAllPage(Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        //设置当前页
        map.put("page",page);
        //设置条件查询对象
        FeedbackExample example = new FeedbackExample();
        //查询总条数
        int count = feedbackMapper.selectCountByExample(example);

        //设置总条数
        map.put("total",count);
        /**  每页展示  2
         *   当前页：1     0,2
         *   当前页：2     2,2
         *   当前页：3     4,2    5~6
         *   当前页：4     6,2    5~6
         *   当前页：5     8,2    5~6
         *
         *   (page-1)*pageSize
         * */
        //创建分页对象 参数：起始条数,数据数
        RowBounds rowBounds = new RowBounds((page - 1) * pageSize, pageSize);

        //根据分页查询数据
        List<Feedback> feedbacks = feedbackMapper.selectByExampleAndRowBounds(example, rowBounds);
        //设置数据
        map.put("rows",feedbacks);
        return map;
    }

    @Override
    public HashMap<String, Object> delete(Feedback feedback) {
        HashMap<String, Object> map = new HashMap<>();
        //删除数据
        try {
            feedbackMapper.delete(feedback);
            map.put("message","数据删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("message","数据删除失败");
        }
        return map;
    }
}
