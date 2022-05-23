package root.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateUserDto {
    @NotNull
    private String name;
}
