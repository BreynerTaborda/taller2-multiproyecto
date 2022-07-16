package co.com.poli.shoppingservice.clientFeign;

import co.com.poli.shoppingservice.helpers.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service", fallback = CustomerClientImplHystrixFallBack.class)
public interface CustomerClient {

    @GetMapping("/store/api/v1/customers/numberID/{numberID}")
    Response findByNumberID(@PathVariable("numberID") String numberID);

    @GetMapping("/store/api/v1/customers/{id}")
    Response findByID(@PathVariable("id") Long id);

}
