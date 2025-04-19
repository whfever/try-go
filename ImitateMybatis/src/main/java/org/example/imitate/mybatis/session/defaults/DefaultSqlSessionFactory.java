package org.example.imitate.mybatis.session.defaults;

import org.example.imitate.mybatis.session.SqlSession;
import org.example.imitate.mybatis.session.SqlSessionFactory;
import org.example.imitate.mybatis.builder.xml.XMLConfigBuilder;
import org.example.imitate.mybatis.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSqlSessionFactory.class);
    private final Configuration configuration;

    public DefaultSqlSessionFactory(InputStream inputStream) {
        try {
            // 解析配置文件
            XMLConfigBuilder builder = new XMLConfigBuilder(inputStream);
            this.configuration = builder.parse();
            logger.info("SqlSessionFactory初始化完成");
        } catch (Exception e) {
            logger.error("SqlSessionFactory初始化失败", e);
            throw new RuntimeException("SqlSessionFactory初始化失败", e);
        }
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
} 