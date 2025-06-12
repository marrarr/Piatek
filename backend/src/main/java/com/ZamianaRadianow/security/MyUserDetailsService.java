package com.ZamianaRadianow.security;

import com.ZamianaRadianow.security.user.DBUser;
import com.ZamianaRadianow.security.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DBUser DBUser = userRepository.findByUsername(username);
        if (DBUser == null) throw new UsernameNotFoundException("User not found");

        List<SimpleGrantedAuthority> authorities = DBUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .toList();

        return new org.springframework.security.core.userdetails.User(
                DBUser.getUsername(), DBUser.getPassword(), authorities);
    }
}

