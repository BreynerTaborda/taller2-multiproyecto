package co.com.poli.bookingsservice.service.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class BookingsInDTO {
    @NotNull
    private Long userId;

    @NotNull
    private Long showtimeId;

    private List<BookingsItemInDTO> movies;

}
