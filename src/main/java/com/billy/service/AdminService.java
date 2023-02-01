package com.billy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.billy.pojo.JAdmin;

import java.util.List;

public interface AdminService {
    //根据账号密码查询
    public JAdmin queryById(QueryWrapper<JAdmin> wrapper);

    //查询所有管理员
    public List<JAdmin> queryAll();

    //添加管理员
    public void insert(JAdmin admin);

    //删除管理员
    void delete(Integer id);

    //修改管理员
    void update(JAdmin admin);
}
