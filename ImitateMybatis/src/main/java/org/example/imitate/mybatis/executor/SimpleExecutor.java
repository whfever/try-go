package org.example.imitate.mybatis.executor;

import org.example.imitate.mybatis.config.Configuration;
import org.example.imitate.mybatis.mapping.MappedStatement;
import org.example.imitate.mybatis.parameter.ParameterHandler;
import org.example.imitate.mybatis.result.ResultSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.List;

public class SimpleExecutor implements Executor {
    private static final Logger logger = LoggerFactory.getLogger(SimpleExecutor.class);
    private final Configuration configuration;
    private Connection connection;

    public SimpleExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) {
        try {
            PreparedStatement ps = prepareStatement(ms, parameter);
            ResultSet rs = ps.executeQuery();
            return new ResultSetHandler(configuration).handleResultSets(rs, ms);
        } catch (SQLException e) {
            logger.error("Error executing query", e);
            throw new RuntimeException("Error executing query", e);
        }
    }

    @Override
    public int update(MappedStatement ms, Object parameter) {
        try {
            PreparedStatement ps = prepareStatement(ms, parameter);
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error executing update", e);
            throw new RuntimeException("Error executing update", e);
        }
    }

    private PreparedStatement prepareStatement(MappedStatement ms, Object parameter) throws SQLException {
        if (connection == null) {
            connection = getConnection();
        }
        PreparedStatement ps = connection.prepareStatement(ms.getSql());
        new ParameterHandler(configuration, ms, parameter).setParameters(ps);
        return ps;
    }

    private Connection getConnection() throws SQLException {
        try {
            Class.forName(configuration.getDriver());
            return DriverManager.getConnection(
                configuration.getUrl(),
                configuration.getUsername(),
                configuration.getPassword()
            );
        } catch (ClassNotFoundException e) {
            logger.error("Error loading driver", e);
            throw new RuntimeException("Error loading driver", e);
        }
    }

    @Override
    public void commit() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.commit();
            }
        } catch (SQLException e) {
            logger.error("Error committing transaction", e);
            throw new RuntimeException("Error committing transaction", e);
        }
    }

    @Override
    public void rollback() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error("Error rolling back transaction", e);
            throw new RuntimeException("Error rolling back transaction", e);
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Error closing connection", e);
            throw new RuntimeException("Error closing connection", e);
        }
    }
} 