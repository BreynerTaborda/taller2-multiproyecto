package co.com.poli.showtimesservice.clientFeign;

import co.com.poli.showtimesservice.helpers.Response;
import co.com.poli.showtimesservice.helpers.ResponseBuild;
import co.com.poli.showtimesservice.model.Movies;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MoviesClientImplHystrixFallBack implements MoviesClient{

    private final ResponseBuild responseBuild;


    @Override
    public Response findById(Long id) {
        return this.responseBuild.failedServerUnavailable(new Movies());
    }
}
