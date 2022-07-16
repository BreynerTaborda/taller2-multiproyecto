package co.com.poli.shoppingservice.clientFeign;

import co.com.poli.shoppingservice.helpers.Response;
import co.com.poli.shoppingservice.helpers.ResponseBuild;
import co.com.poli.shoppingservice.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductClientImplHystrixFallBack implements ProductClient {

    private final ResponseBuild build;

    @Override
    public Response findById(Long id) {
        return build.success(new Product());
    }
}
