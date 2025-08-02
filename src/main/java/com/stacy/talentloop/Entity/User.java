package com.stacy.talentloop.Entity;

import com.stacy.talentloop.DTO.UserRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User implements UserDetails {
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;
    private String fullName;

    @Indexed(unique = true)
    private String email;
    private String password;

    private String availability;
    private String bio;
    private UserRole role;

    private List<String> skills = new ArrayList<>();
    private String profileImageUrl;

    private List<String> likedBy = new ArrayList<>();
    private List<String> disLikedBy = new ArrayList<>();

    @DBRef
    private List<User> connections = new ArrayList<>();

    public String getRealUsername(){
        return username;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername(){
        return email;
    }

    @Override
    public String getPassword(){
        return password;
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
