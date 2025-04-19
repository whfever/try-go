package org.example.imitate.mybatis.executor;

import org.example.imitate.mybatis.mapping.MappedStatement;

import java.util.List;

public interface Executor {
    <E> List<E> query(MappedStatement ms, Object parameter);
    int update(MappedStatement ms, Object parameter);
    void commit();
    void rollback();
    void close();
} 