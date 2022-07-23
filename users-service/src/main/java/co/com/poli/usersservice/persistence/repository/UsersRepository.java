package co.com.poli.usersservice.persistence.repository;

import co.com.poli.usersservice.persistence.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

}
