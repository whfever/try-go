package org.example.imitate.mybatis.session;

import org.example.imitate.mybatis.config.Configuration;

import java.util.List;

public interface SqlSession  extends AutoCloseable {
    /**
     * 根据指定的SQL ID获取一条记录的封装对象
     */
    <T> T selectOne(String statement, Object parameter);

    /**
     * 获取多条记录
     */
    <E> List<E> selectList(String statement, Object parameter);

    /**
     * 插入记录
     */
    int insert(String statement, Object parameter);

    /**
     * 更新记录
     */
    int update(String statement, Object parameter);

    /**
     * 删除记录
     */
    int delete(String statement, Object parameter);

    /**
     * 获取Mapper
     */
    <T> T getMapper(Class<T> type);

    /**
     * 提交事务
     */
    void commit();

    /**
     * 回滚事务
     */
    void rollback();

    /**
     * 关闭会话
     */
    void close();

    Configuration getConfiguration();
} 