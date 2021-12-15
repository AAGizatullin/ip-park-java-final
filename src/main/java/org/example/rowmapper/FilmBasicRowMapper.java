package org.example.rowmapper;

import org.example.domain.FilmBasicModel;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FilmBasicRowMapper implements RowMapper<FilmBasicModel> {
    @Override
    public FilmBasicModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new FilmBasicModel(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                (String[]) rs.getArray("genre").getArray(),
                rs.getFloat("rating"),
                rs.getString("image")

        );
    }
}
