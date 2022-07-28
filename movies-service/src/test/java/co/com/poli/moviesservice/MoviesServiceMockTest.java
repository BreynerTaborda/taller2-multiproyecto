package co.com.poli.moviesservice;

import co.com.poli.moviesservice.clientFeign.BookingsClient;
import co.com.poli.moviesservice.clientFeign.ShowtimesClient;
import co.com.poli.moviesservice.persistence.entity.Movies;
import co.com.poli.moviesservice.persistence.repository.MoviesRepository;
import co.com.poli.moviesservice.service.MoviesService;
import co.com.poli.moviesservice.service.MoviesServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class MoviesServiceMockTest {

    @Mock
    private MoviesRepository moviesRepository;

    private MoviesService moviesService;

    @Mock
    private  BookingsClient bookingsClient;

    @Mock
    private  ShowtimesClient showtimesClient;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);

        moviesService = new MoviesServiceImpl(moviesRepository, bookingsClient, showtimesClient);

        Movies movies = new Movies();

        movies.setTitle("tes");
        movies.setDirector("tes");
        movies.setRating(1);
        movies.setId(1L);

        Optional<Movies> moviesOptional = Optional.ofNullable(movies);

        Mockito.when(moviesRepository.findById(1L))
                .thenReturn(moviesOptional);
    }

    @Test
    public void whenFindByProjectIdentifier_returnProject(){
        Movies movies = this.moviesService.findById(1L);

        Assertions.assertThat(movies.getId()).isEqualTo(1);

    }
}
