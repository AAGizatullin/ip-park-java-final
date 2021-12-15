package org.example.manager;

import lombok.RequiredArgsConstructor;
import org.example.domain.SaleModel;
import org.example.domain.SaleModel;
import org.example.domein.SaleFilmModel;
import org.example.dto.SaleRegisterRequestDTO;
import org.example.dto.SaleRegisterResponseDTO;
import org.example.exception.FilmNotFoundException;
import org.example.exception.FilmPriceChangedException;
import org.example.exception.SaleRegistrationException;
import org.example.rowmapper.SaleFilmRowMapper;
import org.example.rowmapper.SaleRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class SaleManager {
    private final NamedParameterJdbcTemplate template;
    private final SaleFilmRowMapper saleFilmRowMapper;
    private final SaleRowMapper saleRowMapper;

    //register
    @Transactional
    public SaleRegisterResponseDTO register(SaleRegisterRequestDTO requestDTO) {
        try {
            final SaleFilmModel item = template.queryForObject(
                    //language=PostgreSQL
                    """
                            SELECT id,name,price FROM films
                            WHERE id=:id AND removed=FALSE

                            """,
                    Map.of("id", requestDTO.getFilmId()),
                    saleFilmRowMapper
            );

            if (item.getPrice() != requestDTO.getPrice()) {
                throw new FilmPriceChangedException("film with id " + item.getId() + " has price " + item.getPrice() + " but requested " + requestDTO.getPrice());
            }
            final int affected = template.update(
                    //language=PostgreSQL
                    """
                            UPDATE films SET views=views+1 WHERE id=:filmId AND removed = FALSE
                            """,
                    Map.of(
                            "filmId", requestDTO.getFilmId()

                    )
            );

            final SaleModel sale = template.queryForObject(
                    //language=PostgreSQL
                    """
                            INSERT INTO sales(film_id, name,price) VALUES (:filmId, :name, :price)
                            RETURNING id, film_id, name, price
                                 """,
                    Map.of(
                            "filmId", requestDTO.getFilmId(),
                            "name", item.getName(),
                            "price", requestDTO.getPrice()


                    ),
                    saleRowMapper

            );
            return new SaleRegisterResponseDTO(new SaleRegisterResponseDTO.Sale(
                    sale.getId(),
                    sale.getFilmId(),
                    sale.getName(),
                    sale.getPrice()
            ));


        } catch (EmptyResultDataAccessException e) {
            throw new FilmNotFoundException(e);
        }

    }
}
