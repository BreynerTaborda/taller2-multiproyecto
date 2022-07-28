package co.com.poli.usersservice;

import co.com.poli.usersservice.persistence.entity.Users;
import co.com.poli.usersservice.persistence.repository.UsersRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class UsersRepositoryMockTest {

    @Autowired
    private UsersRepository usersRepository;


    @Test
    public void findAll_return_ListMovies(){
        Users users = new Users();

        users.setName("Test");
        users.setLastname("test");

        this.usersRepository.save(users);

        List<Users> projects = this.usersRepository.findAll();

        Assertions.assertThat(projects.size()).isEqualTo(1);
    }
}
