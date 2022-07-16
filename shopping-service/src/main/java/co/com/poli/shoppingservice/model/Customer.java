package co.com.poli.shoppingservice.model;

import lombok.Data;

@Data
public class Customer {
    private Long id;
    private String numberID;
    private String name;
    private String email;
}
