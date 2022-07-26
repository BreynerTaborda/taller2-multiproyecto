package co.com.poli.bookingsservice.service;


import co.com.poli.bookingsservice.clientFeign.MoviesClient;
import co.com.poli.bookingsservice.clientFeign.ShowtimesClient;
import co.com.poli.bookingsservice.clientFeign.UsersClient;
import co.com.poli.bookingsservice.model.Movies;
import co.com.poli.bookingsservice.model.Showtimes;
import co.com.poli.bookingsservice.model.Users;
import co.com.poli.bookingsservice.persistence.entity.Bookings;
import co.com.poli.bookingsservice.persistence.entity.BookingsItem;
import co.com.poli.bookingsservice.persistence.repository.BookingsItemRepository;
import co.com.poli.bookingsservice.persistence.repository.BookingsRepository;
import co.com.poli.bookingsservice.service.dto.BookingsDetalleInDTO;
import co.com.poli.bookingsservice.service.dto.BookingsInDTO;
import co.com.poli.bookingsservice.service.dto.BookingsItemInDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingsServiceImpl implements BookingsService {

    private final BookingsRepository bookingsRepository;

    private final BookingsItemRepository bookingsItemRepository;

    private final MoviesClient moviesClient;

    private final ShowtimesClient showtimesClient;

    private final UsersClient usersClient;

    @Override
    /**
     * return Bookings
     * Cuando el id del Bookings es:
     *  userID o showtimesID = -1L : El servicio esta ciado
     *  bookingsID = -2L : Esta caido el servicio de movies
     *  userID o showtimesID = -3L : No existe un registro con el userId o showtimeID
     *  bookingsID = -4L : Alguna de las movie no existe
     * 
     */
    public Bookings save(BookingsInDTO bookingsInDTO) {
        Boolean servicioCaido = false;
        Boolean notFound = false;
        Bookings bookings = new Bookings();
        bookings.setShowtimeID(bookingsInDTO.getShowtimeId());
        bookings.setUserID(bookingsInDTO.getUserId());

        Bookings bookingsRespuesta = new Bookings();

        int users = usersClient.findById(bookingsInDTO.getUserId()).getCode();
        if(users == 404){
            bookingsRespuesta.setId(-3L);
            bookingsRespuesta.setUserID(-3L);
            notFound = true;
        }else if(users == 503){
            bookingsRespuesta.setId(-1L);
            bookingsRespuesta.setUserID(-1L);
            servicioCaido = true;
            notFound = true;
        }

        int showtimes = showtimesClient.findById(bookingsInDTO.getShowtimeId()).getCode();
        if(showtimes == 404){
            bookingsRespuesta.setId(-3L);
            bookingsRespuesta.setShowtimeID(-3L);
        }else if(showtimes == 503){
            bookingsRespuesta.setId(-1L);
            bookingsRespuesta.setShowtimeID(-1L);
            servicioCaido = true;
        }

        int validarServicioMovie = moviesClient.findById(1L).getCode();
        if(validarServicioMovie == 503){
            bookingsRespuesta.setId(-2L);
            servicioCaido = true;
        }

        if(!servicioCaido && !notFound){
            List<BookingsItem> bookingsItems = new ArrayList<>();
            List<BookingsItem> movieInexistente = new ArrayList<>();
            for(BookingsItemInDTO bookingsItem: bookingsInDTO.getMovies()){
                BookingsItem nuevoItem = new BookingsItem();
                nuevoItem.setIdMovie(bookingsItem.getIdMovie());
                bookingsItems.add(nuevoItem);
                int movie = moviesClient.findById(bookingsItem.getIdMovie()).getCode();
                if(movie == 404){
                    movieInexistente.add(nuevoItem);
                }
            }

            bookings.setMovies(bookingsItems);

            if(movieInexistente.isEmpty()){
                return this.bookingsRepository.save(bookings);
            }else{
                bookingsRespuesta.setId(-4L);
                bookingsRespuesta.setMovies(movieInexistente);
            }
        }


        return  bookingsRespuesta;
    }

    /**
     *
     * Tener en cuenta:
     * Cuando hay algun servico esta caido se devolvera en la lista un BookingsDetalleInDTO con:
     *
     *
     */
    @Override
    public List<BookingsDetalleInDTO> findAll() {
        Boolean servicioCaido = false;
        List<Bookings> bookings = this.bookingsRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();

        List<BookingsDetalleInDTO> detalleInDTOList = new ArrayList<>();

        BookingsDetalleInDTO bookingsRespuesta = new BookingsDetalleInDTO();

        int users = usersClient.findById(1L).getCode();
        if(users == 503){
            bookingsRespuesta.setId(-1L);
            Users user = new Users();
            user.setId(-1L);
            bookingsRespuesta.setUser(user);
            servicioCaido = true;
        }

        int showtime = showtimesClient.findById(1L).getCode();
        if(showtime == 503){
            bookingsRespuesta.setId(-1L);
            Showtimes showtimes = new Showtimes();
            showtimes.setId(-1L);
            bookingsRespuesta.setShowtimes(showtimes);
            servicioCaido = true;
        }

        int validarServicioMovie = moviesClient.findById(1L).getCode();
        if(validarServicioMovie == 503){
            bookingsRespuesta.setId(-2L);
            servicioCaido = true;
        }

        if(!servicioCaido){
            for(int i = 0; i < bookings.size(); i++){
                BookingsDetalleInDTO bookingsDetalleInDTO = new BookingsDetalleInDTO();

                bookingsDetalleInDTO.setId(bookings.get(i).getId());

                Users user = modelMapper.map(usersClient.findById(bookings.get(i).getUserID()).getData(),Users.class);
                bookingsDetalleInDTO.setUser(user);

                Showtimes showtimes = modelMapper.map(showtimesClient.findById(bookings.get(i).getShowtimeID()).getData(),Showtimes.class);
                bookingsDetalleInDTO.setShowtimes(showtimes);

                List<Movies> movies = bookings.get(i).getMovies().stream()
                        .map(showtimesItem -> {
                            Movies movie = modelMapper.map(moviesClient.findById(showtimesItem.getIdMovie()).getData(),Movies.class);
                            return movie;
                        }).collect(Collectors.toList());

                bookingsDetalleInDTO.setMovies(movies);

                detalleInDTOList.add(bookingsDetalleInDTO);
            }
        }else{
            detalleInDTOList.add(bookingsRespuesta);
        }


        return detalleInDTOList;
    }

    @Override
    public BookingsDetalleInDTO findById(Long id) {
        Boolean servicioCaido = false;
        Optional<Bookings> bookings = this.bookingsRepository.findById(id);

        if(!bookings.isEmpty()){
            BookingsDetalleInDTO bookingsRespuesta = new BookingsDetalleInDTO();

            int users = usersClient.findById(1L).getCode();
            if(users == 503){
                bookingsRespuesta.setId(-1L);
                Users user = new Users();
                user.setId(-1L);
                bookingsRespuesta.setUser(user);
                servicioCaido = true;
            }

            int showtime = showtimesClient.findById(1L).getCode();
            if(showtime == 503){
                bookingsRespuesta.setId(-1L);
                Showtimes showtimes = new Showtimes();
                showtimes.setId(-1L);
                bookingsRespuesta.setShowtimes(showtimes);
                servicioCaido = true;
            }

            int validarServicioMovie = moviesClient.findById(1L).getCode();
            if(validarServicioMovie == 503){
                bookingsRespuesta.setId(-2L);
                servicioCaido = true;
            }


            if(!servicioCaido){
                ModelMapper modelMapper = new ModelMapper();

                BookingsDetalleInDTO bookingsDetalleInDTO = new BookingsDetalleInDTO();
                bookingsDetalleInDTO.setId(bookings.get().getId());

                Users user = modelMapper.map(usersClient.findById(bookings.get().getUserID()).getData(),Users.class);
                bookingsDetalleInDTO.setUser(user);

                Showtimes showtimes = modelMapper.map(showtimesClient.findById(bookings.get().getShowtimeID()).getData(),Showtimes.class);
                bookingsDetalleInDTO.setShowtimes(showtimes);

                List<Movies> movies = bookings.get().getMovies().stream()
                        .map(showtimesItem -> {
                            Movies movie = modelMapper.map(moviesClient.findById(showtimesItem.getIdMovie()).getData(),Movies.class);
                            return movie;
                        }).collect(Collectors.toList());

                bookingsDetalleInDTO.setMovies(movies);

                return bookingsDetalleInDTO;
            }

            return bookingsRespuesta;

        }

        return null;
    }

    @Override
    public List<BookingsDetalleInDTO> findByIdUser(Long id) {
        List<Bookings> bookings = this.bookingsRepository.findByUserID(id);
        List<BookingsDetalleInDTO> detalleInDTOList = new ArrayList<>();
        if(bookings != null){
            for(Bookings booking: bookings){
                detalleInDTOList.add(findById(booking.getId()));
            }
            return detalleInDTOList;
        }

        return null;
    }

    @Override
    public Boolean delete(Long id) {
        Optional<Bookings> bookings = this.bookingsRepository.findById(id);

        if(!bookings.isEmpty()){
            this.bookingsRepository.delete(bookings.get());
            return true;
        }

        return false;
    }

    @Override
    public Boolean validarMovieRegistrada(Long id) {
        List<BookingsItem>  bookingsItem = this.bookingsItemRepository.findByIdMovie(id);

        if(!bookingsItem.isEmpty()){
            return true;
        }

        return false;
    }

    @Override
    public Boolean validarUserRegistrado(Long id) {
        List<Bookings> bookings = this.bookingsRepository.findByUserID(id);

        if(!bookings.isEmpty()){
            return true;
        }

        return false;
    }
}
