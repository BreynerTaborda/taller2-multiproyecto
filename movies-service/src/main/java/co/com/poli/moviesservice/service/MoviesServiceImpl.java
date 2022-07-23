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
        Optional<Movies> movie = this.moviesRepository.findById(id);

        if(!movie.isEmpty()){
            int exiteShowtimes = bookingsClient.validarMovieRegistrada(id).getCode();
            if(exiteShowtimes == 404){
                this.moviesRepository.delete(movie.get());
                return "eliminada";
            }

            return "bookings";
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
