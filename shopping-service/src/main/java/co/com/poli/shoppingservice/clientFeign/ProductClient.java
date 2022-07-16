package co.com.poli.shoppingservice.clientFeign;

import co.com.poli.shoppingservice.helpers.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", fallback = ProductClientImplHystrixFallBack.class)
public interface ProductClient {

    @GetMapping("/store/api/v1/products/{id}")
    Response findById(@PathVariable("id") Long id);

}
