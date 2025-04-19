package org.example.imitate.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FloatTypeHandler implements TypeHandler<Float> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Float parameter) throws SQLException {
        if (parameter == null) {
            ps.setNull(i, java.sql.Types.FLOAT);
        } else {
            ps.setFloat(i, parameter);
        }
    }

    @Override
    public Float getResult(ResultSet rs, int columnIndex) throws SQLException {
        float result = rs.getFloat(columnIndex);
        return rs.wasNull() ? null : result;
    }
} 