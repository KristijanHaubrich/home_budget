package com.example.home_budget.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Getter
@Setter
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="role_id", nullable=false)
    private Role role;

    @Override
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

        simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));

        this.getRole().getPermissions().forEach(
                permission -> simpleGrantedAuthorities.add(new SimpleGrantedAuthority(permission.getName()))
        );

        return simpleGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() { return email; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
