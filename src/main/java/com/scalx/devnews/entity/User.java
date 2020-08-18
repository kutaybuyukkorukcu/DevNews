package com.scalx.devnews.entity;

import com.scalx.devnews.validation.EmailConstraint;
import com.scalx.devnews.validation.PasswordConstraint;
import lombok.Data;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class User extends AbstractAuditable {

    private String username;

    private String password;

    private String email;

    private boolean enabled;
    private boolean tokenExpired;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id")
            )
    private Collection<Role> roles;

}
