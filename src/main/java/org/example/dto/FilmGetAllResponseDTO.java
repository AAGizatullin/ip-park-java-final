package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmGetAllResponseDTO {
private List<Film>films;


@Data
@NoArgsConstructor
@AllArgsConstructor
public static class Film {
    private long id;
    private String name;
    private int price;
    private String[] genre;
    private float rating;
    private String image;
}
}
