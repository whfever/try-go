package org.example.imitate.mybatis.config;

import org.example.imitate.mybatis.mapping.MappedStatement;
import org.example.imitate.mybatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);
    
    private final TypeHandlerRegistry typeHandlerRegistry;
    private final Map<String, MappedStatement> mappedStatements;
    private String driver;
    private String url;
    private String username;
    private String password;

    public Configuration() {
        this.typeHandlerRegistry = new TypeHandlerRegistry();
        this.mappedStatements = new HashMap<>();
    }

    public TypeHandlerRegistry getTypeHandlerRegistry() {
        return typeHandlerRegistry;
    }

    public void addMappedStatement(String id, MappedStatement mappedStatement) {
        mappedStatements.put(id, mappedStatement);
    }

    public MappedStatement getMappedStatement(String id) {
        MappedStatement ms = mappedStatements.get(id);
        if (ms == null) {
            logger.error("No mapped statement found for id: " + id);
            throw new RuntimeException("No mapped statement found for id: " + id);
        }
        return ms;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
} 