package com.manser.pr.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginForm {

    @NotBlank(message = "{NotEmpty.login.email}")
    @Email(message = "{Email.login.email}")
    @Size(max = 100, message = "{Size.login.email}")
    private String email;

    @NotBlank(message = "{NotEmpty.login.password}")
    @Size(min = 6, max = 100, message = "{Size.login.password}")
    private String password;

    public LoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginForm() {}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
