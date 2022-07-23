package co.com.poli.bookingsservice.persistence.repository;


import co.com.poli.bookingsservice.persistence.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Long> {

    Bookings findByUserID(Long id);
}
