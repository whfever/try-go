-- 插入用户测试数据
INSERT INTO `user` (`username`, `password`, `email`) VALUES
('user1', 'password1', 'user1@example.com'),
('user2', 'password2', 'user2@example.com'),
('user3', 'password3', 'user3@example.com');

-- 插入订单测试数据
INSERT INTO `order` (`user_id`, `order_no`, `amount`, `status`) VALUES
(1, 'ORDER001', 100.00, 1),
(1, 'ORDER002', 200.00, 0),
(2, 'ORDER003', 150.00, 1),
(2, 'ORDER004', 300.00, 2),
(3, 'ORDER005', 250.00, 1); 