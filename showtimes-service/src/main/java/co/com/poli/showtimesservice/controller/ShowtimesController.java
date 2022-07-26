package co.com.poli.showtimesservice.controller;




import co.com.poli.showtimesservice.helpers.Response;
import co.com.poli.showtimesservice.helpers.ResponseBuild;
import co.com.poli.showtimesservice.persistence.entity.Showtimes;
import co.com.poli.showtimesservice.persistence.entity.ShowtimesItem;
import co.com.poli.showtimesservice.service.ShowtimesService;
import co.com.poli.showtimesservice.service.dto.ShowtimesDetalleInDTO;
import co.com.poli.showtimesservice.service.dto.ShowtimesInDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/showtimes")
@RequiredArgsConstructor
public class ShowtimesController {

    private final ShowtimesService showtimesService;

    private final ResponseBuild responseBuild;


    @PostMapping
    public Response save(@Valid @RequestBody ShowtimesInDTO showtimesInDTO, BindingResult result) {
       if(result.hasErrors()){
           return this.responseBuild.failed(formatMessage(result));
       }

       Showtimes showtimes = this.showtimesService.save(showtimesInDTO);
       if(showtimes.getId() == -1L){
           return this.responseBuild.failedServer("Esta abajo el servicio de movies");
       }if(showtimes.getId() == -2L){
            String mensajeError = "La(s) movie(s): ";
            for(ShowtimesItem showtimesItem: showtimes.getMovies()){
                mensajeError  += showtimesItem.getIdMovie() + ", ";
            }

            mensajeError += " no existe(n)";
            return this.responseBuild.failedNotFound(mensajeError);
       }

       return this.responseBuild.success(showtimes);
    }

    @GetMapping
    public Response findAll() {
        List<ShowtimesDetalleInDTO> showtimes = this.showtimesService.findAll();

        if(showtimes.size() > 0){
            if(showtimes.get(0).getId() == -1L){
                return this.responseBuild.failedServer("Esta abajo el servicio de movies");
            }

            return this.responseBuild.success(showtimes);
        }

        return this.responseBuild.failedNotFound("No existen showtimes registradas");
    }

    @GetMapping("/{id}")
    public Response findById(@PathVariable("id") Long id) {
        ShowtimesDetalleInDTO showtimes = this.showtimesService.findById(id);

        if(showtimes != null){
            if(showtimes.getId() == -1){
                return this.responseBuild.failedServer("Esta abajo el servicio de movies");
            }

            return this.responseBuild.success(showtimes);
        }

        return this.responseBuild.failedNotFound("No existe showtimes para el id:" + id);
    }

    @PutMapping
    public Response save(@Valid @RequestBody Showtimes showtimes, BindingResult result) {
        if(result.hasErrors()){
            return this.responseBuild.failed(formatMessage(result));
        }

        Showtimes showtimesResult = this.showtimesService.save(showtimes);

        if(showtimesResult == null){
            return this.responseBuild.failedNotFound("No existe showtimes con el id:" + showtimes.getId());
        }else if(showtimesResult.getId() == -1L){
            return this.responseBuild.failedServer("Esta abajo el servicio de movies");
        }else if(showtimesResult.getId() == -2L){
            String mensajeError = "La(s) movie(s): ";
            for(ShowtimesItem showtimesItem: showtimes.getMovies()){
                mensajeError  += showtimesItem.getIdMovie() + ", ";
            }

            mensajeError += " no existe(n)";
            return this.responseBuild.failedNotFound(mensajeError);
        }

        return this.responseBuild.success(showtimes);
    }

    @GetMapping("/movie/{id}")
    public Response validarMovieRegistrada(@PathVariable("id") Long id) {
        Boolean bookingsItem = this.showtimesService.validarMovieRegistrada(id);

        if(bookingsItem){
            return this.responseBuild.success("Existe showtimes para la movie con id: " + id);
        }

        return this.responseBuild.failedNotFound("No existe showtimes para la movie con id: " + id);
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
