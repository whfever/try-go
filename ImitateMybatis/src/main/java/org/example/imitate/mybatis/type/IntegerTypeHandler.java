package org.example.imitate.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntegerTypeHandler implements TypeHandler<Integer> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Integer parameter) throws SQLException {
        if (parameter == null) {
            ps.setNull(i, java.sql.Types.INTEGER);
        } else {
            ps.setInt(i, parameter);
        }
    }

    @Override
    public Integer getResult(ResultSet rs, int columnIndex) throws SQLException {
        int result = rs.getInt(columnIndex);
        return rs.wasNull() ? null : result;
    }
} 