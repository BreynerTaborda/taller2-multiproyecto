package co.com.poli.bookingsservice.controller;

import co.com.poli.bookingsservice.helpers.Response;
import co.com.poli.bookingsservice.helpers.ResponseBuild;
import co.com.poli.bookingsservice.persistence.entity.Bookings;
import co.com.poli.bookingsservice.persistence.entity.BookingsItem;
import co.com.poli.bookingsservice.service.BookingsService;
import co.com.poli.bookingsservice.service.dto.BookingsDetalleInDTO;
import co.com.poli.bookingsservice.service.dto.BookingsInDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingsController {

    private final BookingsService bookingsService;

    private final ResponseBuild responseBuild;


    @PostMapping
    public Response save(@Valid @RequestBody BookingsInDTO bookingsInDTO, BindingResult result) {
       if(result.hasErrors()){
           return this.responseBuild.failed(formatMessage(result));
       }

       Bookings bookings = this.bookingsService.save(bookingsInDTO);

       if(bookings.getId() > 0){
           return this.responseBuild.success(bookings);
       }else if(bookings.getId() == -1L || bookings.getId() == -2L){
           String mensajeError = "Esta(n) abajo  servicio(s) de:";

           if(bookings.getUserID() != null && bookings.getUserID() == -1L){
               mensajeError += " users";
           }

           if(bookings.getShowtimeID() != null && bookings.getShowtimeID() == -1L){
               mensajeError += " showtimes";
           }

           if(bookings.getId() != null && bookings.getId() == -2L){
               mensajeError += " movies";
           }

           return this.responseBuild.failedServer(mensajeError);

       } else if(bookings.getId() == -3L){
           String mensajeError = "";
           if(bookings.getUserID() != null && bookings.getUserID() == -3L){
               mensajeError += "No existe el user con id: " + bookingsInDTO.getUserId() + ", ";
           }

           if(bookings.getShowtimeID() != null && bookings.getShowtimeID() == -3L){
               mensajeError += "No existe el showtimes con id: " + bookingsInDTO.getShowtimeId() + ", ";
           }

           return this.responseBuild.failedNotFound(mensajeError);
       }else if(bookings.getId() == -4L){
           String mensajeError = "La(s) movie(s): ";
           for(BookingsItem bookingsItem: bookings.getMovies()){
               mensajeError  += bookingsItem.getIdMovie() + ", ";
           }

           mensajeError += " no existe(n)";
           return this.responseBuild.failedNotFound(mensajeError);
       }

       return this.responseBuild.success(bookings);
    }

    @GetMapping
    public Response findAll() {
        List<BookingsDetalleInDTO> bookings = this.bookingsService.findAll();

        if(bookings.size() > 0){
            if(bookings.get(0).getId() != null && (bookings.get(0).getId() == -1L || bookings.get(0).getId() == -2L)){
                String mensajeError = "Esta(n) abajo  servicio(s) de:";

                if(bookings.get(0).getUser() != null && bookings.get(0).getUser().getId() != null && bookings.get(0).getUser().getId() == -1L){
                    mensajeError += " users";
                }

                if(bookings.get(0).getShowtimes() != null && bookings.get(0).getShowtimes().getId() != null && bookings.get(0).getShowtimes().getId() == -1L){
                    mensajeError += " showtimes";
                }

                if(bookings.get(0).getId()  != null && bookings.get(0).getId() == -2L ){
                    mensajeError += " movies";
                }

                return this.responseBuild.failedServer(mensajeError);
            }

            return this.responseBuild.success(bookings);
        }

        return this.responseBuild.failedNotFound("No existen bookings registrados");
    }

    @GetMapping("/{id}")
    public Response findById(Long id) {
        BookingsDetalleInDTO bookings = this.bookingsService.findById(id);

        if(bookings != null){
            if(bookings.getId() != null && (bookings.getId() == -1L || bookings.getId() == -2L)){
                String mensajeError = "Esta(n) abajo  servicio(s) de:";

                if(bookings.getUser() != null && bookings.getUser().getId() != null && bookings.getUser().getId() == -1L){
                    mensajeError += " users";
                }

                if(bookings.getShowtimes() != null && bookings.getShowtimes().getId() != null && bookings.getShowtimes().getId() == -1L){
                    mensajeError += " showtimes";
                }

                if(bookings.getId()  != null && bookings.getId() == -2L ){
                    mensajeError += " movies";
                }

                return this.responseBuild.failedServer(mensajeError);
            }

            return this.responseBuild.success(bookings);
        }

        return this.responseBuild.failedNotFound("No existe un bookings para el id:" + id);
    }

    @GetMapping("/users/{id}")
    public Response findByIdUser(@PathVariable("id") Long id) {
        List<BookingsDetalleInDTO> bookings = this.bookingsService.findByIdUser(id);

        if(!bookings.isEmpty()){
            if(bookings.get(0).getId() != null && (bookings.get(0).getId() == -1L || bookings.get(0).getId() == -2L)){
                String mensajeError = "Esta(n) abajo  servicio(s) de:";

                if(bookings.get(0).getUser() != null && bookings.get(0).getUser().getId() != null && bookings.get(0).getUser().getId() == -1L){
                    mensajeError += " users";
                }

                if(bookings.get(0).getShowtimes() != null && bookings.get(0).getShowtimes().getId() != null && bookings.get(0).getShowtimes().getId() == -1L){
                    mensajeError += " showtimes";
                }

                if(bookings.get(0).getId()  != null && bookings.get(0).getId() == -2L ){
                    mensajeError += " movies";
                }

                return this.responseBuild.failedServer(mensajeError);
            }

            return this.responseBuild.success(bookings);
        }

        return this.responseBuild.failedNotFound("No existe un bookings asociado al user con id: " + id);
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable("id") Long id) {
        BookingsDetalleInDTO bookings = this.bookingsService.findById(id);

        if(bookings != null){
            this.bookingsService.delete(bookings.getId());
            return this.responseBuild.success("El bookings con id: " + id + " fue eliminado con exito" );
        }

        return this.responseBuild.failedNotFound("No existe un bookings con id: " + id);
    }

    @GetMapping("/movie/{id}")
    public Response validarMovieRegistrada(@PathVariable("id") Long id) {
        Boolean bookingsItem = this.bookingsService.validarMovieRegistrada(id);

        if(bookingsItem){
            return this.responseBuild.success("Existe bookings para la movie con id: " + id);
        }

        return this.responseBuild.failedNotFound("No existe bookings para la movie con id: " + id);
    }

    @GetMapping("/user/{id}")
    public Response validarUserRegistrado(@PathVariable("id") Long id) {
        Boolean bookings = this.bookingsService.validarUserRegistrado(id);

        if(bookings){
            return this.responseBuild.success("Existe bookings para el user con id: " + id);
        }

        return this.responseBuild.failedNotFound("No existe bookings para el usuer con id: " + id);
    }

//    @PostMapping
//    public Response save(@Valid @RequestBody UsersInDTO users, BindingResult result) {
//        if(result.hasErrors()){
//            return this.responseBuild.failed(formatMessage(result));
//        }
//
//        Users usersEntity = this.usersService.save(users);
//
//        if(usersEntity.getId() == null){
//
//        }
//
//        return this.responseBuild.success(usersEntity);
//    }
//
//    @DeleteMapping("/{id}")
//    public Response delete(@PathVariable("id") Long idUsers) {
//        Boolean elminacionUsuario = this.usersService.delete(idUsers);
//
//        if(elminacionUsuario){
//            return  this.responseBuild.success("El usuario con id:" + idUsers + " fue eliminado.");
//        }
//
//        return this.responseBuild.failedNotFound("El usuario con id:" + idUsers + " no existe.");
//    }
//
//
//    @GetMapping()
//    public Response findAll() {
//        List<Users> usuarios = this.usersService.findAll();
//
//        if(usuarios.size() > 0){
//            return  this.responseBuild.success(usuarios);
//        }
//
//        return this.responseBuild.failedNotFound("No existen usuarios registrados.");
//    }
//
    private List<Map<String, String>> formatMessage(BindingResult result){
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(error ->{
                    Map<String, String> newError = new HashMap<>();
                    newError.put(error.getField(), error.getDefaultMessage());
                    return newError;
                }).collect(Collectors.toList());
        return errors;

    }


}
