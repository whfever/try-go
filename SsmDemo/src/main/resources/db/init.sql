-- 创建数据库
CREATE DATABASE IF NOT EXISTS ssm_demo DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE ssm_demo;

-- 创建用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `email` VARCHAR(100) COMMENT '邮箱',
    `phone` VARCHAR(20) COMMENT '电话号码',
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 插入测试数据
INSERT INTO `user` (`username`, `password`, `email`, `phone`) VALUES
('admin', '123456', 'admin@example.com', '13800138000'),
('zhangsan', '123456', 'zhangsan@example.com', '13800138001'),
('lisi', '123456', 'lisi@example.com', '13800138002'),
('wangwu', '123456', 'wangwu@example.com', '13800138003');
