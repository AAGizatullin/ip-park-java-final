package org.example.rowmapper;

import org.example.domain.FilmFullModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class FilmFullRowMapper implements RowMapper<FilmFullModel> {
    @Override
    public FilmFullModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new FilmFullModel(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getInt("production_year"),
                (String[]) rs.getArray("country").getArray(),
                (String[]) rs.getArray("genre").getArray(),
                rs.getFloat("rating"),
                rs.getLong("views"),
                rs.getString("image")
        );
    }
}
