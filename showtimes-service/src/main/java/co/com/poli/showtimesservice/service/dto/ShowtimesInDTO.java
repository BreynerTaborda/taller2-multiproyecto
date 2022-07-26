package co.com.poli.showtimesservice.service.dto;


import co.com.poli.showtimesservice.persistence.entity.ShowtimesItem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class ShowtimesInDTO {
    @NotNull
    private Date date;
    private List<ShowtimesItemInDTO> movies;

}
