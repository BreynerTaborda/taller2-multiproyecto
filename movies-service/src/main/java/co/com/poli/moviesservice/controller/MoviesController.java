package co.com.poli.moviesservice.controller;




import co.com.poli.moviesservice.helpers.Response;
import co.com.poli.moviesservice.helpers.ResponseBuild;
import co.com.poli.moviesservice.persistence.entity.Movies;
import co.com.poli.moviesservice.service.MoviesService;
import co.com.poli.moviesservice.service.dto.MoviesInDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MoviesController {

    private final MoviesService moviesService;

    private final ResponseBuild responseBuild;


    @PostMapping
    public Response save(@Valid @RequestBody  MoviesInDTO moviesInDTO, BindingResult result) {
       if(result.hasErrors()){
           return this.responseBuild.failed(formatMessage(result));
       }

       Movies movie = this.moviesService.save(moviesInDTO);
       return this.responseBuild.success(movie);
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable("id") Long id) {
        String movieExistente = this.moviesService.delete(id);

        if(movieExistente.equals("inexistente")){
            return this.responseBuild.failedNotFound("La movie con id:" + id + " no existe.");
        }else if(movieExistente.equals("eliminado")){
            return this.responseBuild.success("La movie con id:" + id + " fue eliminada.");
        }


        return this.responseBuild.failedServer(movieExistente);
    }

    @GetMapping
    public Response findAll() {
        List<Movies> movies = this.moviesService.findAll();

        if(movies.size() > 0){
            return this.responseBuild.success(movies);
        }

        return this.responseBuild.failedNotFound("No existen movies registradas");
    }

    @GetMapping("/{id}")
    public Response findById(@PathVariable("id") Long id) {
        Movies movie = this.moviesService.findById(id);

        if(movie != null){
            return this.responseBuild.success(movie);
        }

        return this.responseBuild.failedNotFound("No existe una movie para el id:" + id);
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
