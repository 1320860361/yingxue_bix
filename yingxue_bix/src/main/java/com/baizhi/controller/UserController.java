package com.baizhi.controller;

import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.vo.CommonVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Resource
    UserService userService;

    @RequestMapping("queryAllPage")
    public HashMap<String,Object>queryAllPage(Integer page,Integer pageSize){
        log.info("当前页page: {}",page);
        log.info("每页展示的数据：{}",pageSize);

        HashMap<String, Object> map = userService.queryAllPage(page, pageSize);
        return map;
    }
   /* @RequestMapping("update")
    public HashMap<String,Object>update(@RequestBody User user){
        log.info("修改用户数据: {}",user);
        return userService.update(user);
    }*/
    @RequestMapping("update")
    public CommonVo update(@RequestBody User user){
        log.info("修改用户数据: {}",user);
        return userService.updates(user);
    }

}
