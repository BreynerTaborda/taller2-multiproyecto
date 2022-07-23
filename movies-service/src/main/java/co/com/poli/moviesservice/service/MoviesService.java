package co.com.poli.moviesservice.service;


import co.com.poli.moviesservice.persistence.entity.Movies;
import co.com.poli.moviesservice.service.dto.MoviesInDTO;

import java.util.List;

public interface MoviesService {

    Movies save(MoviesInDTO movies);

    String delete(Long id);

    List<Movies> findAll();

    Movies findById(Long id);

}
