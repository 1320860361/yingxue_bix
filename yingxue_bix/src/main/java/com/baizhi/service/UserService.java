package com.baizhi.service;


import com.baizhi.entity.User;
import com.baizhi.vo.CommonVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

public interface UserService {

    HashMap<String,Object>queryAllPage(Integer page,Integer pageSize);
    HashMap<String,Object>update(User user);
    void delete(User user);
    void add(User user);
    String uploadHeadImg(MultipartFile headImg);
    String uploadHeadImgAliyun(MultipartFile headImg);
    User queryById(String id);
    CommonVo updates(User user);


}
