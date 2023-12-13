package com.example.users.rest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "usuario")
public class User {
    @Id
    private String id;
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",message = "Formato de email no válido")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d{2,}).*$",message = "Formato de contraseña no válido")
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
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
