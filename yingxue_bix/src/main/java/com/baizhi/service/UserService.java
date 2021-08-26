package com.baizhi.service;


import com.baizhi.entity.User;
import com.baizhi.vo.CommonVo;

import java.util.HashMap;

public interface UserService {

    HashMap<String,Object>queryAllPage(Integer page,Integer pageSize);
    HashMap<String,Object>update(User user);

    CommonVo updates(User user);
}
