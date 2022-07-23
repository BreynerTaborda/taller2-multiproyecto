package co.com.poli.moviesservice.service.dto;


import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class MoviesInDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String director;

    @Max(5)
    @Min(1)
    private int rating;
}
