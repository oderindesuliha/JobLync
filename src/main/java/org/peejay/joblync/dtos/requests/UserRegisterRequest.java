package org.peejay.joblync.dtos.requests;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.peejay.joblync.data.models.Role;

@Data
public class UserRegisterRequest{
   @NotBlank(message = "first name is required")
    private String firstName;

    @NotBlank(message = "last name is required")
    private String lastName;

    @NotBlank(message = "phone number is required")
    @Pattern(regexp = "^(\\+234|234|0)(701|702|703|704|705|706|707|708|709|802|803|804|805|806|807|808|809|810|811|812|813|814|815|816|817|818|819|901|902|903|904|905|906|907|908|909)\\d{7}$")
    private String phoneNumber;

    @NotBlank(message = "email is required")
    @Email(message = "Email is invalid")
    private String email;

    private String password;
    
    private String confirmPassword;

    private Role role;
}