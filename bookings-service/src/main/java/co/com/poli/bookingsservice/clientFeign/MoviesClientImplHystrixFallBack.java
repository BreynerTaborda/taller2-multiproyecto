package co.com.poli.bookingsservice.clientFeign;

import co.com.poli.bookingsservice.helpers.Response;
import co.com.poli.bookingsservice.helpers.ResponseBuild;
import co.com.poli.bookingsservice.model.Movies;
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
