package co.com.poli.showtimesservice.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Setter
@Getter
@Entity
@Table(name = "showtimes")
public class Showtimes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    @NotNull
    private Long id;

    @Column
    private Date date;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "showtimes_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<ShowtimesItem> movies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Showtimes showtimes = (Showtimes) o;
        return Objects.equals(id, showtimes.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
