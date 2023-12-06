package com.chernomurov.effectivemobile.test.task.management.system.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class RegisterUserRequest {

        @Email(message = "ERROR -> User email is invalid")
//        @Pattern(regexp = "([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})",
//                message = "ERROR -> User email is invalid;")
        @NotNull(message = "ERROR -> User email is not specified")
        @NotEmpty(message = "ERROR -> User email is not specified")
        private String email;

        @NotNull(message = "ERROR -> User password is not specified")
        @NotEmpty(message = "ERROR -> User password can't be empty")
        private String password;

        @NotNull(message = "ERROR -> User roles are not specified")
        @NotEmpty(message = "ERROR -> User roles can't be empty")
        private Set<String> roleNames;
}
