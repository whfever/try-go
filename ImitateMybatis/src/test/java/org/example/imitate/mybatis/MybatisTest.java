package org.example.imitate.mybatis;

import org.example.imitate.mybatis.entity.User;
import org.example.imitate.mybatis.mapper.UserMapper;
import org.example.imitate.mybatis.session.SqlSession;
import org.example.imitate.mybatis.session.SqlSessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MybatisTest {
    private SqlSessionFactory sqlSessionFactory;

    @BeforeEach
    public void setUp() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resource);
        sqlSessionFactory = SqlSessionFactory.build(inputStream);
    }

    @Test
    public void testSelectById() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.selectById(1L);
            assertNotNull(user);
            assertEquals(1L, user.getId());
            assertEquals("admin", user.getUsername());
            assertEquals("123456", user.getPassword());
            assertEquals("admin@example.com", user.getEmail());
        }
    }

    @Test
    public void testSelectAll() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> users = mapper.selectAll();
            users.forEach(System.out::println);
            assertNotNull(users);
            assertFalse(users.isEmpty());
        }
    }

    @Test
    public void testInsert() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = new User(null, "test", "password", "test@example.com");
            int result = mapper.insert(user);
            assertEquals(1, result);
            assertNotNull(user.getId());
        }
    }

    @Test
    public void testUpdate() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.selectById(1L);
            assertNotNull(user);
            user.setUsername("updated");
            user.setPassword("newpassword");
            user.setEmail("updated@example.com");
            int result = mapper.update(user);
            assertEquals(1, result);
        }
    }

    @Test
    public void testDelete() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int result = mapper.delete(1L);
            assertEquals(1, result);
        }
    }
} 