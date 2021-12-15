package org.example.rowmapper;

import org.example.domein.SaleFilmModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SaleFilmRowMapper implements RowMapper<SaleFilmModel> {
    @Override
    public SaleFilmModel mapRow(ResultSet rs, int rowNum) throws SQLException {
         return new SaleFilmModel(
                 rs.getLong("id"),
                 rs.getString("name"),
                 rs.getInt("price")
         );
    }
}
