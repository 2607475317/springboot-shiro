package com.springbootshiro.service;

import com.springbootshiro.pojo.User;

public interface UserService {
    public User findByName(String name);
    public User findById(Integer id);
}
