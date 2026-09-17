package za.ac.cput.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record UserRequest(
        @NotBlank(message = "Username is required")
        @Size(max = 80, message = "Username must not exceed 80 characters")
        String userName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        @Size(max = 120, message = "Email must not exceed 120 characters")
        String email,

        @NotBlank(message = "Role is required")
        @Pattern(regexp = "(?i)USER|ADMIN", message = "Role must be USER or ADMIN")
        String role
) {
}

