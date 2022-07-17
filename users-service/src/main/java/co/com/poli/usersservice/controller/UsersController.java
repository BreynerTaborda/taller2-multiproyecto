package co.com.poli.usersservice.controller;



import co.com.poli.usersservice.helpers.Response;
import co.com.poli.usersservice.helpers.ResponseBuild;
import co.com.poli.usersservice.persistence.entity.Users;
import co.com.poli.usersservice.service.UsersService;
import co.com.poli.usersservice.service.dto.UsersInDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    private final ResponseBuild responseBuild;

    @PostMapping()
    public Response save(@Valid @RequestBody UsersInDTO users) {
        Users usersEntity = this.usersService.save(users);

        if(usersEntity.getId() == null){

        }

        return this.responseBuild.success(usersEntity);
    }

//    public void delete(Users users) {
//        this.usersRepository.delete(users);
//    }
//
//    public List<Users> findAll() {
//        return this.usersRepository.findAll();
//    }
//
//    public Users findById(Long id) {
//        return this.usersRepository.getById(id);
//    }

}
