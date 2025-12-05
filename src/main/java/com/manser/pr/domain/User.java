package com.manser.pr.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @NotBlank(message = "{NotEmpty.user.name}")
    @Size(max = 100, message = "{Size.user.name}")
    @Column(name = "NAME", length = 100, nullable = false)
    private String name;

    @NotBlank(message = "{NotEmpty.user.email}")
    @Email(message = "{Email.user.email}")
    @Size(max = 100)
    @Column(name = "EMAIL", length = 100, unique = true)
    private String email;

    @NotBlank(message = "{NotEmpty.user.password}")
    @Size(min = 6, max = 100, message = "{Size.user.password}")
    @Column(name = "PASSWORD", length = 100, nullable = false)
    private String password;

    @Pattern(
            regexp = "^\\+?[0-9]{7,20}$",
            message = "{Pattern.user.phone}"
    )
    @Column(name = "PHONE_NUMBER", length = 20)
    private String phone;

    @NotNull(message = "{NotNull.user.userRole}")
    @Enumerated(EnumType.STRING)
    @Column(name = "USER_ROLE")
    private UserRole userRole;

    public User() {
    }

    public User(Long id, String name, String email, String password, String phone, UserRole userRole) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userRole = userRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
