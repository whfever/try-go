package org.example.imitate.mybatis.proxy;

import org.example.imitate.mybatis.config.Configuration;
import org.example.imitate.mybatis.mapping.MappedStatement;
import org.example.imitate.mybatis.session.SqlSession;
import org.example.imitate.mybatis.type.TypeHandler;
import org.example.imitate.mybatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class MapperMethod {
    private static final Logger logger = LoggerFactory.getLogger(MapperMethod.class);
    private final Configuration configuration;
    private final Method method;
    private final Class<?> mapperInterface;
    private final TypeHandlerRegistry typeHandlerRegistry;
    private final String commandName;
    private final SqlCommandType sqlCommandType;

    public MapperMethod(Class<?> mapperInterface, Method method, Configuration configuration) {
        this.mapperInterface = mapperInterface;
        this.method = method;
        this.configuration = configuration;
        this.typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        this.commandName = method.getName();
        this.sqlCommandType = resolveCommandType();
    }

    private SqlCommandType resolveCommandType() {
        String methodName = method.getName();
        if (methodName.startsWith("select")) {
            return SqlCommandType.SELECT;
        } else if (methodName.startsWith("insert")) {
            return SqlCommandType.INSERT;
        } else if (methodName.startsWith("update")) {
            return SqlCommandType.UPDATE;
        } else if (methodName.startsWith("delete")) {
            return SqlCommandType.DELETE;
        }
        throw new RuntimeException("Unknown execution method for: " + methodName);
    }

    public Object execute(SqlSession sqlSession, Object[] args) {
        MappedStatement ms = configuration.getMappedStatement(commandName);
        if (ms == null) {
            throw new RuntimeException("No mapped statement found for id: " + commandName);
        }
        Object result = null;
        switch (sqlCommandType) {
            case SELECT:
                result = executeSelect(sqlSession, ms, args);
                break;
            case INSERT:
                result = executeInsert(sqlSession, ms, args);
                break;
            case UPDATE:
                result = executeUpdate(sqlSession, ms, args);
                break;
            case DELETE:
                result = executeDelete(sqlSession, ms, args);
                break;
            default:
                throw new RuntimeException("Unknown execution method for: " + commandName);
        }
        return result;
    }

    private Object executeSelect(SqlSession sqlSession, MappedStatement ms, Object[] args) {
        List<?> result;
        if (method.getReturnType().equals(List.class)) {
            result = sqlSession.selectList(commandName, args);
        } else {
            result = sqlSession.selectOne(commandName, args);
        }
        return result;
    }

    private Object executeInsert(SqlSession sqlSession, MappedStatement ms, Object[] args) {
        return sqlSession.insert(commandName, args);
    }

    private Object executeUpdate(SqlSession sqlSession, MappedStatement ms, Object[] args) {
        return sqlSession.update(commandName, args);
    }

    private Object executeDelete(SqlSession sqlSession, MappedStatement ms, Object[] args) {
        return sqlSession.delete(commandName, args);
    }

    private enum SqlCommandType {
        SELECT, INSERT, UPDATE, DELETE
    }
} 