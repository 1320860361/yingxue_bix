package com.baizhi.controller;

import com.baizhi.entity.Feedback;
import com.baizhi.service.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("feedback")
public class FeedbackController {

    private static final Logger log = LoggerFactory.getLogger(FeedbackController.class);
    @Resource
    FeedbackService feedbackService;

    @RequestMapping("queryAllPage")
    public HashMap<String,Object>queryAllPage(Integer page,Integer pageSize){
        log.info("当前页page: {}",page);
        log.info("每页展示的数据：{}",pageSize);

        HashMap<String, Object> map = feedbackService.queryAllPage(page, pageSize);

        return map;
    }

    @RequestMapping("delete")
    public HashMap<String,Object>delete(@RequestBody Feedback feedback){
        log.info("接受删除的数据：{}",feedback);
        HashMap<String, Object> delete = feedbackService.delete(feedback);

        return delete;

    }
}
