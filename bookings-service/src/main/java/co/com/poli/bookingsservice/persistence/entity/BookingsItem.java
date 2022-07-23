package co.com.poli.bookingsservice.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Entity
@Table(name = "bookings_item")
public class BookingsItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    @NotNull
    private Long id;

    @Column
    private Long idMovie;
}
