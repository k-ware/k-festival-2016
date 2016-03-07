package kr.co.kware.festival.web;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class InputVO {
    @NotNull
    private int questionId;

    @Pattern(regexp=".+@.+\\.[a-z]+")
    private String email;

    private String name;

    @NotNull
    @NotBlank
    @Min(value = 25)
    private String source;
}
