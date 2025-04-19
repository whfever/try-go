package org.example.springbootdemo.service;

import org.example.springbootdemo.entity.User;
import org.example.springbootdemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Transactional
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Transactional
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Transactional
    public int deleteUser(Integer id) {
        return userMapper.deleteUser(id);
    }
}
