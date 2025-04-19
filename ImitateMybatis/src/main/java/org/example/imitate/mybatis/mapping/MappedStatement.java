package org.example.imitate.mybatis.mapping;

import org.example.imitate.mybatis.type.TypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MappedStatement {
    private static final Logger logger = LoggerFactory.getLogger(MappedStatement.class);
    
    private String id;
    private String sql;
    private Class<?> resultType;
    private List<ParameterMapping> parameterMappings;
    private SqlCommandType sqlCommandType;

    public MappedStatement(String id, String sql, Class<?> resultType, SqlCommandType sqlCommandType) {
        this.id = id;
        this.sql = sql;
        this.resultType = resultType;
        this.sqlCommandType = sqlCommandType;
        this.parameterMappings = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getSql() {
        return sql;
    }

    public Class<?> getResultType() {
        return resultType;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void addParameterMapping(ParameterMapping parameterMapping) {
        parameterMappings.add(parameterMapping);
    }

    public enum SqlCommandType {
        SELECT, INSERT, UPDATE, DELETE
    }
} 