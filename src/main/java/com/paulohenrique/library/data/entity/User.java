package com.paulohenrique.library.data.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Basic(optional = false)
    private String username;

    @Basic(optional = false)
    private String password;

    @Basic(optional = false)
    private String cep;

    @OneToOne
    @JoinColumn(name="role_id")
    private Role role;

    public User() { }

    public User(String name, String password, String cep, Role role) {
        this.username = name;
        this.cep = cep;
        this.password = password;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    public String getRoleName() {
        return role.getAuthority();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
