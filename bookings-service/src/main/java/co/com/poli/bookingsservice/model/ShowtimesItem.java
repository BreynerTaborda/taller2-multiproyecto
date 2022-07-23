package co.com.poli.bookingsservice.model;

import lombok.Data;

import javax.persistence.Column;

@Data
public class ShowtimesItem {
    private Long id;
    private Long idMovie;
}
