package co.com.poli.bookingsservice.service;

import co.com.poli.bookingsservice.persistence.entity.Bookings;
import co.com.poli.bookingsservice.service.dto.BookingsDetalleInDTO;
import co.com.poli.bookingsservice.service.dto.BookingsInDTO;
import java.util.List;

public interface BookingsService {

    Bookings save(BookingsInDTO bookingsInDTO);

    List<BookingsDetalleInDTO> findAll();

    BookingsDetalleInDTO findById(Long id);

    List<BookingsDetalleInDTO> findByIdUser(Long id);

    Boolean delete(Long id);

    Boolean validarMovieRegistrada(Long id);

    Boolean validarUserRegistrado(Long id);
}
