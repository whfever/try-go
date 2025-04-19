package org.example.imitate.mybatis.session;

import org.example.imitate.mybatis.session.defaults.DefaultSqlSessionFactory;

import java.io.InputStream;

public interface SqlSessionFactory  {
    /**
     * 打开一个新的SqlSession
     */
    SqlSession openSession();

    /**
     * 从XML配置文件构建SqlSessionFactory
     */
    static SqlSessionFactory build(InputStream inputStream) {
        return new DefaultSqlSessionFactory(inputStream);
    }
} 