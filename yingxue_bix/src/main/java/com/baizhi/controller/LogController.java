package com.baizhi.controller;

import com.baizhi.service.LogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("log")
public class LogController {

    private static final Logger log = LoggerFactory.getLogger(FeedbackController.class);
    @Resource
    LogService logService;

    @RequestMapping("queryAllPage")
    public HashMap<String,Object>queryAllPage(Integer page,Integer pageSize){
        log.info("当前页page:{}",page);
        log.info("每页展示几条数据pageSize:{}",pageSize);

        HashMap<String, Object> map = logService.queryAllPage(page, pageSize);

        return map;
    }

}
