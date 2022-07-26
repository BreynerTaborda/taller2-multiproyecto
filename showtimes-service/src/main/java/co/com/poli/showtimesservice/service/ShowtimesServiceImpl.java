package co.com.poli.showtimesservice.service;


import co.com.poli.showtimesservice.clientFeign.MoviesClient;
import co.com.poli.showtimesservice.model.Movies;
import co.com.poli.showtimesservice.persistence.entity.Showtimes;
import co.com.poli.showtimesservice.persistence.entity.ShowtimesItem;
import co.com.poli.showtimesservice.persistence.repository.ShowtimesItemRepository;
import co.com.poli.showtimesservice.persistence.repository.ShowtimesRepository;
import co.com.poli.showtimesservice.service.dto.ShowtimesDetalleInDTO;
import co.com.poli.showtimesservice.service.dto.ShowtimesInDTO;
import co.com.poli.showtimesservice.service.dto.ShowtimesItemInDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowtimesServiceImpl implements ShowtimesService {

    private final ShowtimesRepository showtimesRepository;

    private final ShowtimesItemRepository showtimesItemRepository;
    private final MoviesClient moviesClient;

    @Override
    public Showtimes save(ShowtimesInDTO showtimesInDTO) {
        Boolean servicioCaido = false;
        Showtimes showtimes = new Showtimes();
        showtimes.setDate(showtimesInDTO.getDate());

        List<ShowtimesItem> showtimesItems = new ArrayList<>();
        List<ShowtimesItem> movieInexistente = new ArrayList<>();

        Showtimes showtimesRespuesta = new Showtimes();

        int validarServicioMovie = moviesClient.findById(1L).getCode();
        if(validarServicioMovie == 503){
            showtimesRespuesta.setId(-1L);
            servicioCaido = true;
        }

        if(!servicioCaido){
            for(ShowtimesItemInDTO showtimesItem: showtimesInDTO.getMovies()){
                ShowtimesItem nuevoItem = new ShowtimesItem();
                nuevoItem.setIdMovie(showtimesItem.getIdMovie());
                showtimesItems.add(nuevoItem);
                int movie = moviesClient.findById(showtimesItem.getIdMovie()).getCode();
                if(movie == 404){
                    movieInexistente.add(nuevoItem);
                }
            }

            showtimes.setMovies(showtimesItems);

            if(movieInexistente.isEmpty()){
                return this.showtimesRepository.save(showtimes);
            }else{
                showtimesRespuesta.setId(-2L);
                showtimesRespuesta.setMovies(movieInexistente);
            }
        }

        return showtimesRespuesta;
    }

    @Override
    public List<ShowtimesDetalleInDTO> findAll() {
        Boolean servicioCaido = false;
        List<Showtimes> showtimes = this.showtimesRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();

        List<ShowtimesDetalleInDTO> detalleInDTOList = new ArrayList<>();

        ShowtimesDetalleInDTO showtimesRespuesta = new ShowtimesDetalleInDTO();

        int validarServicioMovie = moviesClient.findById(1L).getCode();
        if(validarServicioMovie == 503){
            showtimesRespuesta.setId(-1L);
            servicioCaido = true;
        }

        if(!servicioCaido){
            for(int i = 0; i < showtimes.size(); i++){
                ShowtimesDetalleInDTO showtimesDetalleInDTO = new ShowtimesDetalleInDTO();
                showtimesDetalleInDTO.setDate(showtimes.get(i).getDate());
                showtimesDetalleInDTO.setId(showtimes.get(i).getId());

                List<Movies> items = showtimes.get(i).getMovies().stream()
                        .map(showtimesItem -> {
                            Movies movies = modelMapper.map(moviesClient.findById(showtimesItem.getIdMovie()).getData(),Movies.class);
                            return movies;
                        }).collect(Collectors.toList());

                showtimesDetalleInDTO.setMovies(items);
                detalleInDTOList.add(showtimesDetalleInDTO);
            }

            return detalleInDTOList;
        }

        detalleInDTOList.add(showtimesRespuesta);
        return detalleInDTOList;
    }

    @Override
    public ShowtimesDetalleInDTO findById(Long id) {
        Boolean servicioCaido = false;
        Optional<Showtimes> showtimes = this.showtimesRepository.findById(id);

        if(!showtimes.isEmpty()){
            ShowtimesDetalleInDTO showtimesRespuesta = new ShowtimesDetalleInDTO();

            int validarServicioMovie = moviesClient.findById(1L).getCode();
            if(validarServicioMovie == 503){
                showtimesRespuesta.setId(-1L);
                servicioCaido = true;
            }
            if(!servicioCaido){
                ShowtimesDetalleInDTO showtimesDetalleInDTO = new ShowtimesDetalleInDTO();
                showtimesDetalleInDTO.setId(showtimes.get().getId());
                showtimesDetalleInDTO.setDate(showtimes.get().getDate());

                ModelMapper modelMapper = new ModelMapper();
                List<Movies> movies = showtimes.get().getMovies().stream()
                        .map(showtimesItem -> {
                            Movies movie = modelMapper.map(moviesClient.findById(showtimesItem.getIdMovie()).getData(),Movies.class);
                            return movie;
                        }).collect(Collectors.toList());
                showtimesDetalleInDTO.setMovies(movies);

                return showtimesDetalleInDTO;
            }
            return showtimesRespuesta;
        }
        return null;
    }

    @Override
    public Showtimes save(Showtimes showtimes) {
        Boolean servicioCaido = false;
        Optional<Showtimes> showtimesExistente = this.showtimesRepository.findById(showtimes.getId());

        Showtimes showtimesRespuesta = null;
        if (!showtimesExistente.isEmpty()) {
            List<ShowtimesItem> movieInexistente = new ArrayList<>();
            showtimesRespuesta = new Showtimes();

            int validarServicioMovie = moviesClient.findById(1L).getCode();
            if (validarServicioMovie == 503) {
                showtimesRespuesta.setId(-1L);
                servicioCaido = true;
            }

            if (!servicioCaido) {
                for (ShowtimesItem showtimesItem : showtimes.getMovies()) {
                    int movie = moviesClient.findById(showtimesItem.getIdMovie()).getCode();
                    if (movie == 404) {
                        movieInexistente.add(showtimesItem);
                    }
                }

                if (movieInexistente.isEmpty()) {
                    return this.showtimesRepository.save(showtimes);
                } else {
                    showtimesRespuesta.setId(-2L);
                    showtimesRespuesta.setMovies(movieInexistente);
                }
            }
        }

        return showtimesRespuesta;
    }

    @Override
    public Boolean validarMovieRegistrada(Long id) {
        List<ShowtimesItem> showtimesItem = this.showtimesItemRepository.findByIdMovie(id);

        if(!showtimesItem.isEmpty()){

            for(ShowtimesItem item: showtimesItem){
                System.out.println("____________________________________________");
                System.out.println(item.getId());
            }

            return true;
        }

        return false;
    }


}
