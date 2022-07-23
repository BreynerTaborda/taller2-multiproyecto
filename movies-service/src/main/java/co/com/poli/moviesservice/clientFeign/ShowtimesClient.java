package co.com.poli.moviesservice.clientFeign;

import co.com.poli.moviesservice.helpers.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "showtimes-service")
public interface ShowtimesClient {
    @GetMapping("/store/api/v1/showtimes/movie/{id}")
    Response findById(@PathVariable("id")Long id);
}