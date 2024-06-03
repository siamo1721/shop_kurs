package com.example.kurs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @SequenceGenerator(sequenceName = "user_id_seq", name = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private boolean expired;
    @JsonIgnore
    private boolean locked;
    @JsonIgnore
    private boolean enabled;

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<UserRole> userRoles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream().map(UserRole::getUserAuthority).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return !expired;
    }
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
