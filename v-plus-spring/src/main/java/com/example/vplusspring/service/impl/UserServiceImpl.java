package com.example.vplusspring.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vplusspring.mapper.UserMapper;
import com.example.vplusspring.model.User;
import com.example.vplusspring.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}