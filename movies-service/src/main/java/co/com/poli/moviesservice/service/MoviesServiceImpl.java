package co.com.poli.moviesservice.service;

import co.com.poli.moviesservice.clientFeign.BookingsClient;
import co.com.poli.moviesservice.clientFeign.ShowtimesClient;
import co.com.poli.moviesservice.persistence.entity.Movies;
import co.com.poli.moviesservice.persistence.repository.MoviesRepository;
import co.com.poli.moviesservice.service.dto.MoviesInDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoviesServiceImpl implements MoviesService {

    private final MoviesRepository moviesRepository;

    private final BookingsClient bookingsClient;

    private final ShowtimesClient showtimesClient;

    @Override
    public Movies save(MoviesInDTO moviesInDTO) {
        Movies moviesEntity = new Movies();
        moviesEntity.setTitle(moviesInDTO.getTitle());
        moviesEntity.setDirector(moviesInDTO.getDirector());
        moviesEntity.setRating(moviesInDTO.getRating());

        return this.moviesRepository.save(moviesEntity);
    }

    @Override
    public String delete(Long id) {
        Boolean servicioCaido = false;
        Boolean movieShowtimesVacio = false;
        Boolean movieBookingsVacio = false;

        Optional<Movies> movie = this.moviesRepository.findById(id);

        if(!movie.isEmpty()){
            String mensajeRespuesta = "Esta(n) abajo servicio(s) de: ";
            int exiteShowtimes = showtimesClient.validarMovieRegistrada(id).getCode();
            if(exiteShowtimes == 404){
                movieShowtimesVacio = true;
            }else if(exiteShowtimes == 503){
                servicioCaido = true;
                mensajeRespuesta += "showtimes ";
            }

            int existeBookings = bookingsClient.validarMovieRegistrada(id).getCode();

           if(existeBookings == 404){
                movieBookingsVacio = true;
            }else if(existeBookings == 503){
                servicioCaido = true;
                mensajeRespuesta += "bookings";
            }

           if(!servicioCaido){
               if(movieBookingsVacio && movieShowtimesVacio){
                   this.moviesRepository.delete(movie.get());
                   return "eliminado";
               }

               mensajeRespuesta = "La pelicula con id:" + id + " esta registrada en:";
               if(!movieShowtimesVacio){
                   mensajeRespuesta += " showtimes";
               }

               if(!movieBookingsVacio){
                   mensajeRespuesta += " bookings ";
               }
           }

            return mensajeRespuesta;
        }

        return "inexistente";
    }

    @Override
    public List<Movies> findAll() {
        return this.moviesRepository.findAll();
    }

    @Override
    public Movies findById(Long id) {
        Optional<Movies> movie = this.moviesRepository.findById(id);
        if(!movie.isEmpty()){
            return movie.get();
        }
        return null;
    }

}
