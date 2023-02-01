package com.billy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.billy.mapper.AdminMapper;
import com.billy.pojo.JAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public JAdmin queryById(QueryWrapper<JAdmin> wrapper) {
        return adminMapper.selectOne(wrapper);
    }

    @Override
    public List<JAdmin> queryAll() {
        return adminMapper.selectList(null);
    }

    @Override
    public void insert(JAdmin admin) {
        adminMapper.insert(admin);
    }

    @Override
    public void delete(Integer id) {
        adminMapper.deleteById(id);
    }

    @Override
    public void update(JAdmin admin) {
        adminMapper.updateById(admin);
    }

}
