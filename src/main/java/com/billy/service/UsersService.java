package com.billy.service;

import com.billy.pojo.Goods;

import java.util.List;

public interface UsersService {

    List<Goods> queryAll(String brand,String type);

    Goods queryUsersById(Integer id);

    void add(Goods users);

    void delete(Integer id);

    void update(Goods users);

    Goods login(String username, String password);
}
