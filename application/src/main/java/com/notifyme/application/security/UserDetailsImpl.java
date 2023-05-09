package com.notifyme.application.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.notifyme.application.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Getter
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;
    private final Long IID;
    private final String email;
    @JsonIgnore
    private final String password;

    private final String status;
    private final Collection<? extends GrantedAuthority> authorities;
    private final User user;

    public UserDetailsImpl(Long IID,
                           String email,
                           String password,
                           String status,
                           Collection<? extends GrantedAuthority> authorities,
                           User user) {
        this.IID = IID;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.user = user;
        this.status = status;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority(user.getType().toString()));

        return new UserDetailsImpl(user.getIID(), user.getEmailAddress(),
                user.getPassword(), user.getStatus().toString(), authorities, user);
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.status.toUpperCase(Locale.ROOT).equals("DELETED");
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.status.toUpperCase(Locale.ROOT).equals("LOCKED");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status.toUpperCase(Locale.ROOT).equals("ACTIVE");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl that = (UserDetailsImpl) o;
        return Objects.equals(IID, that.IID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(IID);
    }
}
