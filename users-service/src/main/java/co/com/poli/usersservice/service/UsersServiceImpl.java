package co.com.poli.usersservice.service;

import co.com.poli.usersservice.persistence.entity.Users;
import co.com.poli.usersservice.persistence.repository.UsersRepository;
import co.com.poli.usersservice.service.dto.UsersInDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService{

    private final UsersRepository usersRepository;

    @Override
    public Users save(UsersInDTO users) {
        Users usersEntity = new Users();
        usersEntity.setName(users.getName());
        usersEntity.setLastname(users.getLastname());

        return this.usersRepository.save(usersEntity);
    }

    @Override
    public void delete(Users users) {
        this.usersRepository.delete(users);
    }

    @Override
    public List<Users> findAll() {
        return this.usersRepository.findAll();
    }

    @Override
    public Users findById(Long id) {
        return this.usersRepository.getById(id);
    }
}
