package com.app.restApplication.dtos.requests;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.time.LocalDate;

public class UserReqDTO {

    @NotNull
    @Size(min = 3, max = 255, message = "name field should contain at least 3 characters")
    private String name;

    @Column(unique = true)
    @Email(message = "a valid email should be provided")
    private String email;

    @NotNull
    @Size(min = 7, max = 255, message = "password field should contain at least 7 characters")
    private String password;

    @NotNull
    @Size(min = 3, max = 255, message = "address field should contain at least 3 characters")
    private String address;

    @NotNull
    @Past(message = "birth date must be a valid date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private boolean active = false;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
