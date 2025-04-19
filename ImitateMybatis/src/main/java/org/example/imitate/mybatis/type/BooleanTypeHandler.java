package org.example.imitate.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BooleanTypeHandler implements TypeHandler<Boolean> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Boolean parameter) throws SQLException {
        if (parameter == null) {
            ps.setNull(i, java.sql.Types.BOOLEAN);
        } else {
            ps.setBoolean(i, parameter);
        }
    }

    @Override
    public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
        boolean result = rs.getBoolean(columnIndex);
        return rs.wasNull() ? null : result;
    }
} 