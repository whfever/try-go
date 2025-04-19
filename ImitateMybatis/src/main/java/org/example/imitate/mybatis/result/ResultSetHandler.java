package org.example.imitate.mybatis.result;

import org.example.imitate.mybatis.config.Configuration;
import org.example.imitate.mybatis.mapping.MappedStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSetHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResultSetHandler.class);
    private final Configuration configuration;

    public ResultSetHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    public <E> List<E> handleResultSets(ResultSet rs, MappedStatement ms) throws SQLException {
        List<E> result = new ArrayList<>();
        Class<?> resultType = ms.getResultType();
        while (rs.next()) {
            try {
                E obj = (E) resultType.newInstance();
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rs.getObject(i);
                    String fieldName = convertToCamelCase(columnName);
                    try {
                        Field field = resultType.getDeclaredField(fieldName);
                        field.setAccessible(true);
                        field.set(obj, value);
                    } catch (NoSuchFieldException e) {
                        logger.warn("Field not found: {} in class {}", fieldName, resultType.getName());
                    }
                }
                result.add(obj);
            } catch (Exception e) {
                throw new RuntimeException("Error creating result object", e);
            }
        }
        return result;
    }

    private String convertToCamelCase(String columnName) {
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        for (int i = 0; i < columnName.length(); i++) {
            char currentChar = columnName.charAt(i);
            if (currentChar == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(currentChar));
                    nextUpper = false;
                } else {
                    result.append(Character.toLowerCase(currentChar));
                }
            }
        }
        return result.toString();
    }
} 