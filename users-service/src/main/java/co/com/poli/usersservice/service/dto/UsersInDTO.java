package co.com.poli.usersservice.service.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UsersInDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String lastname;
}
