package co.com.poli.bookingsservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
public class Showtimes {
    private Long id;
    private Date date;
    private List<Movies> movies;
}
