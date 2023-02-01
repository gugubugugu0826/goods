package com.billy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.billy.mapper.UsersMapper;
import com.billy.pojo.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public List<Goods> queryAll(String brand,String type) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(brand)){
            queryWrapper.lambda().like(Goods::getBrand,brand);
        }
        if(StringUtils.isNotBlank(type)){
            queryWrapper.lambda().like(Goods::getType,type);
        }
        return usersMapper.selectList(queryWrapper);
    }

    @Override
    public Goods queryUsersById(Integer id) {
        return usersMapper.selectById(id);
    }

    @Override
    public void add(Goods users) {
        usersMapper.insert(users);
    }

    @Override
    public void delete(Integer id) {
        usersMapper.deleteById(id);
    }

    @Override
    public void update(Goods users) {
        usersMapper.updateById(users);
    }

    @Override
    public Goods login(String username, String password) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().eq(Goods::getPhone,username).eq(Goods::getPassword,password);
        List<Goods> users = usersMapper.selectList(queryWrapper);
        if(users.size()==0)
            return null;
        return usersMapper.selectList(queryWrapper).get(0);
    }
}
