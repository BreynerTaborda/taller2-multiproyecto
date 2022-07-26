package co.com.poli.bookingsservice.clientFeign;

import co.com.poli.bookingsservice.helpers.Response;
import co.com.poli.bookingsservice.helpers.ResponseBuild;
import co.com.poli.bookingsservice.model.Movies;
import co.com.poli.bookingsservice.model.Showtimes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShowtimesClientImplHystrixFallBack implements ShowtimesClient{

    private final ResponseBuild responseBuild;


    @Override
    public Response findById(Long id) {
        return this.responseBuild.failedServerUnavailable(new Showtimes());
    }
}
