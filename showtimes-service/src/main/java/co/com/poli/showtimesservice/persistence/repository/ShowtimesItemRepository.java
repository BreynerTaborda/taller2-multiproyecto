package co.com.poli.showtimesservice.persistence.repository;

import co.com.poli.showtimesservice.persistence.entity.ShowtimesItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowtimesItemRepository extends JpaRepository<ShowtimesItem, Long> {
    List<ShowtimesItem> findByIdMovie(Long id);
}
