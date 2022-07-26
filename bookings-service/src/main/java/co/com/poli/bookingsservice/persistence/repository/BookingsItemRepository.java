package co.com.poli.bookingsservice.persistence.repository;

import co.com.poli.bookingsservice.persistence.entity.BookingsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingsItemRepository extends JpaRepository<BookingsItem, Long> {

    List<BookingsItem> findByIdMovie(Long id);
}
