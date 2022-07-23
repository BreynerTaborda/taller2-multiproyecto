package co.com.poli.usersservice.service;

import co.com.poli.usersservice.persistence.entity.Users;
import co.com.poli.usersservice.persistence.repository.UsersRepository;
import co.com.poli.usersservice.service.dto.UsersInDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Boolean delete(Long idUsers) {
        Optional<Users> users = this.usersRepository.findById(idUsers);

        if(!users.isEmpty()){
            this.usersRepository.delete(users.get());
            return true;
        }

        return false;
    }

    @Override
    public List<Users> findAll() {
        return this.usersRepository.findAll();
    }

    @Override
    public Users findById(Long id) {
        Optional<Users> user = this.usersRepository.findById(id);

        if(!user.isEmpty()){
            return user.get();
        }

        return null;
    }

}
