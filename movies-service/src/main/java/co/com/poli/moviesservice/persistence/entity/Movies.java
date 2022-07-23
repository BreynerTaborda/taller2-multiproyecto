package co.com.poli.moviesservice.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Entity
@Table(name = "movies")
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    @NotNull
    private Long id;

    @Column
    private String title;

    @Column
    private String director;

    @Column
    private int rating;

}
