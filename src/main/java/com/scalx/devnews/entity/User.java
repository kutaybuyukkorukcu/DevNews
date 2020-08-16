package com.scalx.devnews.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user")
@Data
public class User extends AbstractAuditable {

    private String username;
    private String password;

//    @EmailValidator
//    private String email;
//    private boolean isActive;

//    private boolean enabled;
//    private boolean tokenExpired;

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
