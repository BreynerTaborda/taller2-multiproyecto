package co.com.poli.showtimesservice.helpers;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ResponseBuild {

    public Response successCreated(Object data){
        Response response = new Response.Builder()
                .code(HttpStatus.CREATED.value())
                .data(data)
                .build();

        return response;
    }

    public Response success(Object data){
        Response response = new Response.Builder()
                .code(HttpStatus.OK.value())
                .data(data)
                .build();

        return response;
    }

    public Response failed(Object data){
        Response response = new Response.Builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .data(data)
                .build();

        return response;
    }

    public Response failedNotFound(Object data){
        Response response = new Response.Builder()
                .code(HttpStatus.NOT_FOUND.value())
                .data(data)
                .build();

        return response;
    }

    public Response failedServer(Object data){
        Response response = new Response.Builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .data(data)
                .build();

        return response;
    }

    public Response failedServerUnavailable(Object data){
        Response response = new Response.Builder()
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .data(data)
                .build();

        return response;
    }

}
