package co.com.poli.bookingsservice.model;

import lombok.Data;

import javax.persistence.Column;

@Data
public class Users {
    private Long id;
    private String name;
    private String lastname;
}
