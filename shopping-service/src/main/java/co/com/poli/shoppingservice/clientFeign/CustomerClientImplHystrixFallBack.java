package co.com.poli.shoppingservice.clientFeign;

import co.com.poli.shoppingservice.helpers.Response;
import co.com.poli.shoppingservice.helpers.ResponseBuild;
import co.com.poli.shoppingservice.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerClientImplHystrixFallBack implements CustomerClient{

    private final ResponseBuild build;

    @Override
    public Response findByNumberID(String numberID) {
        return build.success(new Customer());
    }

    @Override
    public Response findByID(Long id) {
        return build.success(new Customer());
    }
}
