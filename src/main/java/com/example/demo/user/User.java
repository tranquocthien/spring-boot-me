package com.example.demo.user;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import jakarta.validation.constraints.Email;

import java.util.Collection;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table()
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 20)
    private String name;

//    private String companyId;
//    private String branchId;

    private String hash;

    private String salt;

    private String token;

    private String accessToken;

    private String invitationToken;

    private String deactivatedDatetime;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Date createdDatetime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @LastModifiedDate
    private Date updatedDatetime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }


    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

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
        return true;
    }
}