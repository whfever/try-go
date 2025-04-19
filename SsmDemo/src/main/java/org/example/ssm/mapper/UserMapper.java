package org.example.ssm.mapper;

import org.example.ssm.entity.User;
import java.util.List;

public interface UserMapper {
    User getUserById(Integer id);
    List<User> getAllUsers();
    int addUser(User user);
    int updateUser(User user);
    int deleteUser(Integer id);
} 