package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FilmBasicModel {
    private long id;
    private String name;
    private int price;
    private String[] genre;
    private float rating;
    private String image;
}
