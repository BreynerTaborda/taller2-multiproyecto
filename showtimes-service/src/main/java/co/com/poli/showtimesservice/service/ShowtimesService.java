package co.com.poli.showtimesservice.service;

import co.com.poli.showtimesservice.persistence.entity.Showtimes;
import co.com.poli.showtimesservice.service.dto.ShowtimesDetalleInDTO;
import co.com.poli.showtimesservice.service.dto.ShowtimesInDTO;

import java.util.List;

public interface ShowtimesService {

    Showtimes save(ShowtimesInDTO showtimesInDTO);

    List<ShowtimesDetalleInDTO> findAll();

    ShowtimesDetalleInDTO findById(Long id);

    Showtimes save(Showtimes showtimes);

    Boolean validarMovieRegistrada(Long id);
}
