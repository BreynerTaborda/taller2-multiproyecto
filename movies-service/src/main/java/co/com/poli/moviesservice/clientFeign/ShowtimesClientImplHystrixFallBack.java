package co.com.poli.moviesservice.clientFeign;

import co.com.poli.moviesservice.helpers.Response;
import co.com.poli.moviesservice.helpers.ResponseBuild;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShowtimesClientImplHystrixFallBack implements ShowtimesClient{

    private final ResponseBuild responseBuild;


    @Override
    public Response validarMovieRegistrada(Long id) {
        return this.responseBuild.failedServerUnavailable("");
    }
}
