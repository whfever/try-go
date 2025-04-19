package org.example.imitate.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface TypeHandler<T> {
    /**
     * 设置参数
     */
    void setParameter(PreparedStatement ps, int i, T parameter) throws SQLException;

    /**
     * 获取结果
     */
    T getResult(ResultSet rs, int columnIndex) throws SQLException;
} 