package root.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateUserDto {
    @NotNull
    private String name;
}
