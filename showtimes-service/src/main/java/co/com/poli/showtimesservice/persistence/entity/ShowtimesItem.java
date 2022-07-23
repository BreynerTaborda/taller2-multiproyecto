package co.com.poli.showtimesservice.persistence.entity;

import co.com.poli.showtimesservice.model.Movies;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Entity
@Table(name = "showtimes_item")
public class ShowtimesItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    @NotNull
    private Long id;

    @Column
    private Long idMovie;
}
