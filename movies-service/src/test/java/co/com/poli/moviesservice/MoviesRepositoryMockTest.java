package co.com.poli.moviesservice;

import co.com.poli.moviesservice.helpers.ResponseBuild;
import co.com.poli.moviesservice.persistence.entity.Movies;
import co.com.poli.moviesservice.persistence.repository.MoviesRepository;
import co.com.poli.moviesservice.service.MoviesService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class MoviesRepositoryMockTest {

    @Autowired
    private MoviesRepository moviesRepository;


    @Test
    public void findAll_return_ListMovies(){
        Movies movies = new Movies();

        movies.setTitle("Test");
        movies.setDirector("test");
        movies.setRating(1);


        this.moviesRepository.save(movies);

        List<Movies> projects = this.moviesRepository.findAll();

        Assertions.assertThat(projects.size()).isEqualTo(1);
    }
}
