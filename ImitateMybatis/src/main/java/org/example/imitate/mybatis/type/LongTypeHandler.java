package org.example.imitate.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LongTypeHandler implements TypeHandler<Long> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Long parameter) throws SQLException {
        if (parameter == null) {
            ps.setNull(i, java.sql.Types.BIGINT);
        } else {
            ps.setLong(i, parameter);
        }
    }

    @Override
    public Long getResult(ResultSet rs, int columnIndex) throws SQLException {
        long result = rs.getLong(columnIndex);
        return rs.wasNull() ? null : result;
    }
} 