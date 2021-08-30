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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.nio.channels.MulticastChannel;
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
    @RequestMapping("add")
    public CommonVo add(@RequestBody User user){
        log.info("添加用户数据: {}",user);
        try {
            userService.add(user);
            return CommonVo.success("添加成功!!!");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonVo.faild("添加失败！！！");
        }
    }
    @RequestMapping("uploadHeadImg")
    public HashMap<String,String>uploadHeadImg(MultipartFile headImg){
        log.info("文件名：{}",headImg.getOriginalFilename());
        log.info("文件大小：{}",headImg.getSize());
        log.info("文件类型：{}",headImg.getContentType());
        String msg = userService.uploadHeadImgAliyun(headImg);
        HashMap<String,String>map=new HashMap<>();
        map.put("fileName",msg);
        return map;
    }
    @RequestMapping("queryById")
    public User queryById(String id){
        log.info("查询管理员id：{}",id);
        return userService.queryById(id);
    }
    @RequestMapping("delete")
    public  CommonVo delete(@RequestBody User user) {
        log.info("删除用户数据------：{}", user);

        try {
            userService.delete(user);
            return CommonVo.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("============"+e.getMessage());
            return CommonVo.faild("删除失败");
        }
    }
}
