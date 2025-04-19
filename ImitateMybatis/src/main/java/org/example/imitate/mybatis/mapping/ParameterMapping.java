package org.example.imitate.mybatis.mapping;

import org.example.imitate.mybatis.type.TypeHandler;

public class ParameterMapping {
    private String property;
    private Class<?> javaType;
    private TypeHandler<?> typeHandler;

    public ParameterMapping(String property, Class<?> javaType, TypeHandler<?> typeHandler) {
        this.property = property;
        this.javaType = javaType;
        this.typeHandler = typeHandler;
    }

    public String getProperty() {
        return property;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public TypeHandler<?> getTypeHandler() {
        return typeHandler;
    }
} 