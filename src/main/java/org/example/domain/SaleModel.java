package org.example.domain;//package org.example.domein;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaleModel {
    private long id;
    private long filmId;
    private String name;
    private int price;
}
