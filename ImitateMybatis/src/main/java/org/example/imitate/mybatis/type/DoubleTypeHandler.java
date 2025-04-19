package org.example.imitate.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoubleTypeHandler implements TypeHandler<Double> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Double parameter) throws SQLException {
        if (parameter == null) {
            ps.setNull(i, java.sql.Types.DOUBLE);
        } else {
            ps.setDouble(i, parameter);
        }
    }

    @Override
    public Double getResult(ResultSet rs, int columnIndex) throws SQLException {
        double result = rs.getDouble(columnIndex);
        return rs.wasNull() ? null : result;
    }
} 