package com.baizhi.service;

import com.baizhi.entity.Log;

import java.util.HashMap;

public interface LogService {

    HashMap<String,Object>queryAllPage(Integer page,Integer pageSize);

    void add(Log log);
}
