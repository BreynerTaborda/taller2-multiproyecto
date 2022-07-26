package co.com.poli.showtimesservice.service.dto;

import co.com.poli.showtimesservice.model.Movies;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ShowtimesDetalleInDTO {
    private Long id;
    private Date date;
    private List<Movies> movies;
}
