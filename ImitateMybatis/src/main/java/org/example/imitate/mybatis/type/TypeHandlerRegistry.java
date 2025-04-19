package org.example.imitate.mybatis.type;

import java.util.HashMap;
import java.util.Map;

public class TypeHandlerRegistry {
    private final Map<Class<?>, TypeHandler<?>> typeHandlerMap = new HashMap<>();

    public TypeHandlerRegistry() {
        register(String.class, new StringTypeHandler());
        register(Integer.class, new IntegerTypeHandler());
        register(Long.class, new LongTypeHandler());
    }

    public <T> void register(Class<T> type, TypeHandler<? extends T> typeHandler) {
        typeHandlerMap.put(type, typeHandler);
    }

    @SuppressWarnings("unchecked")
    public <T> TypeHandler<T> getTypeHandler(Class<T> type) {
        return (TypeHandler<T>) typeHandlerMap.get(type);
    }
} 