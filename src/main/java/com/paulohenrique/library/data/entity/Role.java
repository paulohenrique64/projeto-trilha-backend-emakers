package com.paulohenrique.library.data.entity;

import com.paulohenrique.library.util.Roles;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    @Column(nullable = false)
    private String roleName;

    public Role() {}

    public Role(Roles role) {
        this.roleName = role.name();
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleName(Roles role) {
        this.roleName = role.name();
    }

    @Override
    public String getAuthority() {
        return roleName;
    }
}
