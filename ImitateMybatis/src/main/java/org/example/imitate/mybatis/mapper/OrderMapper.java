package org.example.imitate.mybatis.mapper;

import org.example.imitate.mybatis.entity.Order;
import java.util.List;

public interface OrderMapper {
    // 根据ID查询订单
    Order selectById(Long id);
    
    // 根据订单号查询订单
    Order selectByOrderNo(String orderNo);
    
    // 查询用户的所有订单
    List<Order> selectByUserId(Long userId);
    
    // 查询指定状态的订单
    List<Order> selectByStatus(Integer status);
    
    // 插入订单
    int insert(Order order);
    
    // 更新订单状态
    int updateStatus(Long id, Integer status);
    
    // 删除订单
    int deleteById(Long id);
} 