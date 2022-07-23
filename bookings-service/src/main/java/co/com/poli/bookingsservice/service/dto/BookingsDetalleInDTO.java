package co.com.poli.bookingsservice.service.dto;

import co.com.poli.bookingsservice.model.Movies;
import co.com.poli.bookingsservice.model.Showtimes;
import co.com.poli.bookingsservice.model.Users;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BookingsDetalleInDTO {
    private Long id;
    private Users user;
    private Showtimes showtimes;
    private List<Movies> movies;
}
