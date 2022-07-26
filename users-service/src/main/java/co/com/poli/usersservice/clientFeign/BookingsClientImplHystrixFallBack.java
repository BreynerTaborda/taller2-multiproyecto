package co.com.poli.usersservice.clientFeign;

import co.com.poli.usersservice.helpers.Response;
import co.com.poli.usersservice.helpers.ResponseBuild;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingsClientImplHystrixFallBack implements BookingsClient {

    private final ResponseBuild responseBuild;

    @Override
    public Response validarUserRegistrado(Long id) {
        return this.responseBuild.failedServerUnavailable("");
    }
}


