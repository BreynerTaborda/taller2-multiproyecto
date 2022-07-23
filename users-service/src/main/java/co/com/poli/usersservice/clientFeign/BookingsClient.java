package co.com.poli.usersservice.clientFeign;

import co.com.poli.usersservice.helpers.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bookings-service", fallback = BookingsClientImplHystrixFallBack.class)
public interface BookingsClient {

    @GetMapping("/store/api/v1/bookings/user/{id}")
    Response validarUserRegistrado(@PathVariable("id") Long id);
}
