package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FilmSearchByNameResponseDTO {
    private List<Film>films;
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Film{
        private long id;
        private String name;
        private int price;
        private int productionYear;
        private String[] country;
        private String[] genre;
        private float rating;
        private long views;
        private String image;
    }
}
