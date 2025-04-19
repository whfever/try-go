package org.example.imitate.mybatis.parameter;

import org.example.imitate.mybatis.config.Configuration;
import org.example.imitate.mybatis.mapping.MappedStatement;
import org.example.imitate.mybatis.mapping.ParameterMapping;
import org.example.imitate.mybatis.type.TypeHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ParameterHandler {
    private final Configuration configuration;
    private final MappedStatement mappedStatement;
    private final Object parameterObject;

    public ParameterHandler(Configuration configuration, MappedStatement mappedStatement, Object parameterObject) {
        this.configuration = configuration;
        this.mappedStatement = mappedStatement;
        this.parameterObject = parameterObject;
    }

    public void setParameters(PreparedStatement ps) throws SQLException {
        List<ParameterMapping> parameterMappings = mappedStatement.getParameterMappings();
        if (parameterMappings != null) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                String propertyName = parameterMapping.getProperty();
                Object value = parameterObject;
                @SuppressWarnings("unchecked")
                TypeHandler<Object> typeHandler = (TypeHandler<Object>) configuration.getTypeHandlerRegistry().getTypeHandler(value.getClass());
                typeHandler.setParameter(ps, i + 1, value);
            }
        }
    }
} 