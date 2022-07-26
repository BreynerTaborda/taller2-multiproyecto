package co.com.poli.bookingsservice.clientFeign;

import co.com.poli.bookingsservice.helpers.Response;
import co.com.poli.bookingsservice.helpers.ResponseBuild;
import co.com.poli.bookingsservice.model.Showtimes;
import co.com.poli.bookingsservice.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsersClientImplHystrixFallBack implements UsersClient{

    private final ResponseBuild responseBuild;


    @Override
    public Response findById(Long id) {
        return this.responseBuild.failedServerUnavailable(new Users());
    }
}
