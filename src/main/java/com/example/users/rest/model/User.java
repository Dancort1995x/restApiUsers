package com.example.users.rest.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "usuario")
public class User {
    @Id
    private String id;
    @NotNull
    @NotEmpty(message = "no puede estar vacío")
    private String name;
    @NotNull
    @NotEmpty(message = "no puede estar vacío")
    private String email;
    @NotNull
    @NotEmpty(message = "no puede estar vacío")
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @NotNull(message = "no debe ser nulo")
    private List<Phone> phones;

    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private boolean isActive;

    @PrePersist
    protected void onCreate() {
        created = LocalDateTime.now();
        modified = created;
        lastLogin = created;
        isActive = true;
        id = UUID.randomUUID().toString();
    }

    @PreUpdate
    protected void onUpdate() {
        modified = LocalDateTime.now();
    }


}
