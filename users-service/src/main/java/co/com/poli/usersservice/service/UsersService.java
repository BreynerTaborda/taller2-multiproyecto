package co.com.poli.usersservice.service;


import co.com.poli.usersservice.persistence.entity.Users;
import co.com.poli.usersservice.service.dto.UsersInDTO;

import java.util.List;

public interface UsersService {

    Users save(UsersInDTO users);

    void delete(Users users);

    List<Users> findAll();

    Users findById(Long id);

}
