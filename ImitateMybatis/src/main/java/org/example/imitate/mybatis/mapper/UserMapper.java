package org.example.imitate.mybatis.mapper;

import org.example.imitate.mybatis.entity.User;
import java.util.List;

public interface UserMapper {
    // 根据ID查询用户
    User selectById(Long id);
    
    // 根据用户名查询用户
    User selectByUsername(String username);
    
    // 查询所有用户
    List<User> selectAll();
    
    // 插入用户
    int insert(User user);
    
    // 更新用户
    int update(User user);
    
    // 删除用户
    int delete(Long id);
} 