package com.baizhi.serviceimpl;


import com.baizhi.dao.LogMapper;
import com.baizhi.entity.Log;
import com.baizhi.entity.LogExample;
import com.baizhi.service.LogService;
import com.baizhi.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Resource
    LogMapper logMapper;
    @Override
    public HashMap<String, Object> queryAllPage(Integer page, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        //设置当前页
        map.put("page",page);
        //设置查询条件对象
        LogExample example = new LogExample();
        example.setOrderByClause("option_time desc"); //排序
        //查询总条数
        int count = logMapper.selectCountByExample(example);
        //设置总条数
        map.put("total",count);
        //创建分页对象  参数：起始条数,数据数
        RowBounds rowBounds = new RowBounds((page-1)*pageSize,pageSize);
        //根据分页查询数据
        List<Log> logs = logMapper.selectByExampleAndRowBounds(example, rowBounds);
        //设置数据
        map.put("rows",logs);
        return map;
    }
    //以非实物的方式运行，如果当前存在事务则挂起当前事务，
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void add(Log log) {
        log.setId(UUIDUtil.getUUID());
        logMapper.insertSelective(log);
    }
}
