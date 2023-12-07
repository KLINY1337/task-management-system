package com.chernomurov.effectivemobile.test.task.management.system.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class AuthenticateUserRequest implements Serializable {

    @Email(message = "ERROR -> User email is invalid")
    @NotNull(message = "ERROR -> User email is not specified")
    @NotEmpty(message = "ERROR -> User email is not specified")
    private final String email;

    @NotNull(message = "ERROR -> User password is not specified")
    @NotEmpty(message = "ERROR -> User password can't be empty")
    private final String password;
}
