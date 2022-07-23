package co.com.poli.usersservice.controller;



import co.com.poli.usersservice.helpers.Response;
import co.com.poli.usersservice.helpers.ResponseBuild;
import co.com.poli.usersservice.persistence.entity.Users;
import co.com.poli.usersservice.service.UsersService;
import co.com.poli.usersservice.service.dto.UsersInDTO;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    private final ResponseBuild responseBuild;

    @PostMapping
    public Response save(@Valid @RequestBody UsersInDTO users, BindingResult result) {
        if(result.hasErrors()){
            return this.responseBuild.failed(formatMessage(result));
        }

        Users usersEntity = this.usersService.save(users);

        if(usersEntity.getId() == null){

        }

        return this.responseBuild.success(usersEntity);
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable("id") Long idUsers) {
        String elminacionUsuario = this.usersService.delete(idUsers);

        if(elminacionUsuario.equals("inexistente")){
            return this.responseBuild.failedNotFound("El user con id:" + idUsers + " no existe.");
        }else if(elminacionUsuario.equals("eliminado")){
            return  this.responseBuild.success("El usuario con id:" + idUsers + " fue eliminado.");
        }

        return this.responseBuild.failedServer(elminacionUsuario);
    }


    @GetMapping()
    public Response findAll() {
        List<Users> usuarios = this.usersService.findAll();

        if(usuarios.size() > 0){
            return  this.responseBuild.success(usuarios);
        }

        return this.responseBuild.failedNotFound("No existen usuarios registrados.");
    }

    @GetMapping("/{id}")
    public Response findById(@PathVariable("id") Long id) {
       Users user = this.usersService.findById(id);

        if(user != null){
            return this.responseBuild.success(user);
        }

        return this.responseBuild.failedNotFound("No existe el user con id: " + id);
    }

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
