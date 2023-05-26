package net.javaguides.springboot.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

//    USER FIRST NAME SHOULD NOT BE NULL OR EMPTY
//    WE ARE USING HIBERNATE VALIDATOR FOR BEAN VALIDATION AND IMPORTED THE DEPENDENCIES.
    @NotEmpty(message = "Firsname should not be Empty")
    private String firstName;
    @NotEmpty
    private String lastName;
//EMAIL SHOULD BE VALID AND NON EMPTY
    @NotEmpty
    @Email(message = "Email should be valid")
    private String email;
}
