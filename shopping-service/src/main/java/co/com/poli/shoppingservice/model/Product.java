package co.com.poli.shoppingservice.model;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private Double price;
    private Double stock;
    private Category category;

}
