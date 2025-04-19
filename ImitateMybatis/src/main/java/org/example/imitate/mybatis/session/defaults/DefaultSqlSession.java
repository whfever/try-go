package org.example.imitate.mybatis.session.defaults;

import org.example.imitate.mybatis.session.SqlSession;
import org.example.imitate.mybatis.config.Configuration;
import org.example.imitate.mybatis.executor.Executor;
import org.example.imitate.mybatis.executor.SimpleExecutor;
import org.example.imitate.mybatis.mapping.MappedStatement;
import org.example.imitate.mybatis.proxy.MapperProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.List;

public class DefaultSqlSession implements SqlSession {
    private static final Logger logger = LoggerFactory.getLogger(DefaultSqlSession.class);
    private final Configuration configuration;
    private final Executor executor;
    private boolean autoCommit;

    public DefaultSqlSession(Configuration configuration) {
        this(configuration, false);
    }

    public DefaultSqlSession(Configuration configuration, boolean autoCommit) {
        this.configuration = configuration;
        this.executor = new SimpleExecutor(configuration);
        this.autoCommit = autoCommit;
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        List<T> list = this.selectList(statement, parameter);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new RuntimeException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
        } else {
            return null;
        }
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        try {
            MappedStatement ms = configuration.getMappedStatement(statement);
            return executor.query(ms, parameter);
        } catch (Exception e) {
            logger.error("查询失败", e);
            throw new RuntimeException("查询失败", e);
        }
    }

    @Override
    public int insert(String statement, Object parameter) {
        return update(statement, parameter);
    }

    @Override
    public int update(String statement, Object parameter) {
        try {
            MappedStatement ms = configuration.getMappedStatement(statement);
            return executor.update(ms, parameter);
        } catch (Exception e) {
            logger.error("更新失败", e);
            throw new RuntimeException("更新失败", e);
        }
    }

    @Override
    public int delete(String statement, Object parameter) {
        return update(statement, parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        MapperProxy<T> mapperProxy = new MapperProxy<>(this, type);
        return (T) Proxy.newProxyInstance(
            type.getClassLoader(),
            new Class[]{type},
            mapperProxy
        );
    }

    @Override
    public void commit() {
        try {
            executor.commit();
        } catch (Exception e) {
            logger.error("提交事务失败", e);
            throw new RuntimeException("提交事务失败", e);
        }
    }

    @Override
    public void rollback() {
        try {
            executor.rollback();
        } catch (Exception e) {
            logger.error("回滚事务失败", e);
            throw new RuntimeException("回滚事务失败", e);
        }
    }

    @Override
    public void close() {
        try {
            executor.close();
        } catch (Exception e) {
            logger.error("关闭会话失败", e);
            throw new RuntimeException("关闭会话失败", e);
        }
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
} 