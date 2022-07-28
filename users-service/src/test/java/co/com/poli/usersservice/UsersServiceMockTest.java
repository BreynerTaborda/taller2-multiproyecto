package co.com.poli.usersservice;

import co.com.poli.usersservice.clientFeign.BookingsClient;
import co.com.poli.usersservice.persistence.entity.Users;
import co.com.poli.usersservice.persistence.repository.UsersRepository;
import co.com.poli.usersservice.service.UsersService;
import co.com.poli.usersservice.service.UsersServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class UsersServiceMockTest {

    @Mock
    private UsersRepository usersRepository;

    private UsersService usersService;

    @Mock
    private BookingsClient bookingsClient;


    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);

        usersService = new UsersServiceImpl(usersRepository, bookingsClient);

        Users users = new Users();

        users.setName("test");
        users.setLastname("test");
        users.setId(1L);

        Optional<Users> usersOptional = Optional.ofNullable(users);

        Mockito.when(usersRepository.findById(1L))
                .thenReturn(usersOptional);
    }

    @Test
    public void whenFindByProjectIdentifier_returnProject(){
        Users users = this.usersService.findById(1L);

        Assertions.assertThat(users.getId()).isEqualTo(1);

    }
}
