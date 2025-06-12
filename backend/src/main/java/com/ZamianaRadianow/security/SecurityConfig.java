package com.ZamianaRadianow.security;

import com.ZamianaRadianow.security.rola.DBRole;
import com.ZamianaRadianow.security.user.DBUser;
import com.ZamianaRadianow.security.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            DBUser DBUser = userRepository.findByUsername(username);

            if (DBUser == null) {
                throw new UsernameNotFoundException("User not found");
            }

            Set<DBRole> DBRoles = DBUser.getRoles();

            List<GrantedAuthority> authorities = new ArrayList<>();
            for (DBRole DBRole : DBRoles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_"+ DBRole.getName()));
            }

            return new org.springframework.security.core.userdetails.User(DBUser.getUsername(),
                    DBUser.getPassword(), true, true, true, true, authorities);
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                //.cors(withDefaults())  // Umożliwia obsługę CORS
                //.csrf(csrf -> csrf.disable()) // Wyłączanie CSRF dla prostoty, w produkcji powinno być włączone
                .authorizeHttpRequests(auth -> auth
                                     .anyRequest().permitAll()
                )
                .httpBasic(withDefaults()); // Użycie podstawowego uwierzytelniania HTTP

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() { //Obsługa CORS
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000") // Dostosuj to do adresu twojej aplikacji front-end
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
            }
        };
    }
}
