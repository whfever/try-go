package com.example.vplusspring.controller;

import com.example.vplusspring.model.User;
import com.example.vplusspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    List<User> globalUserList = new ArrayList<>(); // 全局集合，确保对象不被垃圾回收
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        // 示例：向列表中插入一个新用户


        User newUser = new User();
        newUser.setId(0);
        newUser.setName("Test User");
        newUser.setEmail("test@example.com");
        newUser.setAge(25);

        users.add(newUser);
        globalUserList.add(newUser); // 将对象添加到全局集合中



        return userService.list();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getById(id);
    }

    @PostMapping
    public boolean createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public boolean updateUser(@PathVariable int id, @RequestBody User user) {
        user.setId(id);


        return userService.updateById(user);
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable int id) {
        return userService.removeById(id);
    }
}