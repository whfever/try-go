package org.example.imitate.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StringTypeHandler implements TypeHandler<String> {
    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter) throws SQLException {
        if (parameter == null) {
            ps.setNull(i, java.sql.Types.VARCHAR);
        } else {
            ps.setString(i, parameter);
        }
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        String result = rs.getString(columnIndex);
        return rs.wasNull() ? null : result;
    }
} 