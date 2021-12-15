package org.example.manager;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.example.domain.FilmBasicModel;
import org.example.domain.FilmFullModel;
import org.example.dto.*;
import org.example.exception.FilmNotFoundException;
import org.example.rowmapper.FilmBasicRowMapper;
import org.example.rowmapper.FilmFullRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Data
public class FilmManager {
    private final NamedParameterJdbcTemplate template;
    private final FilmFullRowMapper filmFullRowMapper;
    private final FilmBasicRowMapper filmBasicRowMapper;
    private final String defaultImage = "noimage.png";


    //Get ALL
    public FilmGetAllResponseDTO getAll() {

        final List<FilmBasicModel> items = template.query(
                //language=PostgreSQL
                """
                        SELECT id,name,price,genre,rating,image FROM films
                        WHERE removed=FALSE
                        ORDER BY id
                        LIMIT 1000

                        """,
                filmBasicRowMapper
        );

        final FilmGetAllResponseDTO responseDTO = new FilmGetAllResponseDTO(new ArrayList<>(items.size()));
        for (FilmBasicModel item : items) {
            responseDTO.getFilms().add(new FilmGetAllResponseDTO.Film(
                    item.getId(),
                    item.getName(),
                    item.getPrice(),
                    item.getGenre(),
                    item.getRating(),
                    item.getImage()
            ));
        }
        return responseDTO;
    }

    //Get by id
    public FilmGetByIdResponseDTO getById(long id) {

        try {

            final FilmFullModel item = template.queryForObject(
                    //language=PostgreSQL
                    """
                            SELECT id,name,price,production_year,country,genre,rating,views,image FROM films
                            WHERE id=:id AND removed=FALSE

                            """,
                    Map.of("id", id),
                    filmFullRowMapper
            );
            final FilmGetByIdResponseDTO responseDTO = new FilmGetByIdResponseDTO(new FilmGetByIdResponseDTO.Film(
                    item.getId(),
                    item.getName(),
                    item.getPrice(),
                    item.getProductionYear(),
                    item.getCountry(),
                    item.getGenre(),
                    item.getRating(),
                    item.getViews(),
                    item.getImage()
            ));
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new FilmNotFoundException(e);
        }
    }

    //SearchByName
    public FilmSearchByNameResponseDTO searchByName(String text) {

        try {

            final List<FilmFullModel> items = template.query(
                    //language=PostgreSQL
                    """
                            SELECT id,name,price,production_year,country,genre,rating,views,image FROM films
                            WHERE name ILIKE :name AND removed=FALSE
                                                        
                            """,
                    Map.of("name", "%" + text.trim().toLowerCase() + "%"),
                    filmFullRowMapper
            );
            final FilmSearchByNameResponseDTO responseDTO = new FilmSearchByNameResponseDTO(new ArrayList<>(items.size()));
            for (FilmFullModel item : items) {
                responseDTO.getFilms().add(new FilmSearchByNameResponseDTO.Film(
                        item.getId(),
                        item.getName(),
                        item.getPrice(),
                        item.getProductionYear(),
                        item.getCountry(),
                        item.getGenre(),
                        item.getRating(),
                        item.getViews(),
                        item.getImage()
                ));
            }
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new FilmNotFoundException(e);
        }
    }

    // SearchByGenre
    public FilmSearchByGenreResponseDTO searchByGenre(String text) {
        try {

            final List<FilmFullModel> items = template.query(
                    //language=PostgreSQL
                    """
                            SELECT id,name,price,production_year,country,genre,rating,views,image FROM films
                            WHERE genre ILIKE :genre AND removed=FALSE
                            
                            """,
                    Map.of("genre", "%" + text.trim().toLowerCase() + "%"),
                    filmFullRowMapper
            );
            final FilmSearchByGenreResponseDTO responseDTO = new FilmSearchByGenreResponseDTO(new ArrayList<>(items.size()));
            for (FilmFullModel item : items) {
                responseDTO.getFilms().add(new FilmSearchByGenreResponseDTO.Film(
                        item.getId(),
                        item.getName(),
                        item.getPrice(),
                        item.getProductionYear(),
                        item.getCountry(),
                        item.getGenre(),
                        item.getRating(),
                        item.getViews(),
                        item.getImage()
                ));
            }
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new FilmNotFoundException(e);
        }
    }

    //Save (create/update)
    public FilmSaveResponseDTO save(FilmSaveRequestDTO requestDTO) {
        return requestDTO.getId() == 0 ? create(requestDTO) : update(requestDTO);
    }

    //Create
    private FilmSaveResponseDTO create(FilmSaveRequestDTO requestDTO) {

        final FilmFullModel item = template.queryForObject(
                //language=PostgreSQL
                """
                        INSERT INTO films(name,price,production_year,country,genre,rating,views,image) VALUES (:name,:price,:productionYear,:country,:genre,:rating,:views,:image)
                        RETURNING id,name,price,production_year,country,genre,rating,views,image
                        """,
                Map.of(
                        "name", requestDTO.getName(),
                        "price", requestDTO.getPrice(),
                        "productionYear", requestDTO.getProductionYear(),
                        "country", requestDTO.getCountry(),
                        "genre", requestDTO.getGenre(),
                        "rating", requestDTO.getRating(),
                        "views", requestDTO.getViews(),
                        "image", getImage(requestDTO.getImage())
                ),
                filmFullRowMapper
        );
        final FilmSaveResponseDTO responseDTO = new FilmSaveResponseDTO(new FilmSaveResponseDTO.Film(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getProductionYear(),
                item.getCountry(),
                item.getGenre(),
                item.getRating(),
                item.getViews(),
                item.getImage()
        ));
        return responseDTO;
    }

    //Update
    private FilmSaveResponseDTO update(FilmSaveRequestDTO requestDTO) {

        try {

            final FilmFullModel item = template.queryForObject(
                    //language=PostgreSQL
                    """
                            UPDATE films SET name=:name,price=:price,production_year=:productionYear,country=:country,genre=:genre,rating=:rating,views=:views,image=:image
                            WHERE id=:id AND removed =FALSE
                            RETURNING id,name,price,production_year,country,genre,rating,views,image
                            """,
                    Map.of("id", requestDTO.getId(),
                            "name", requestDTO.getName(),
                            "price", requestDTO.getPrice(),
                            "productionYear", requestDTO.getProductionYear(),
                            "country", requestDTO.getCountry(),
                            "genre", requestDTO.getGenre(),
                            "rating", requestDTO.getRating(),
                            "views", requestDTO.getViews(),
                            "image", getImage(requestDTO.getImage())
                    ),
                    filmFullRowMapper
            );
            final FilmSaveResponseDTO responseDTO = new FilmSaveResponseDTO(new FilmSaveResponseDTO.Film(
                    item.getId(),
                    item.getName(),
                    item.getPrice(),
                    item.getProductionYear(),
                    item.getCountry(),
                    item.getGenre(),
                    item.getRating(),
                    item.getViews(),
                    item.getImage()
            ));
            return responseDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new FilmNotFoundException();
        }
    }

    // Remove by id
    public void removeById(long id) {
        final int affected = template.update(
                //language=PostgreSQL
                """
                        UPDATE films SET removed=TRUE WHERE id=:id
                        """,
                Map.of("id", id)
        );
        if (affected == 0) {
            throw new FilmNotFoundException("film with id " + id + " not found");
        }
    }

    // Restore by id
    public void restoreById(long id) {
        template.update(
                //language=PostgreSQL
                """
                        UPDATE films SET removed=FALSE WHERE id=:id
                        """,
                Map.of("id", id)
        );
    }

    // Get image
    private String getImage(String image) {
        return image == null ? defaultImage : image;
    }


}
