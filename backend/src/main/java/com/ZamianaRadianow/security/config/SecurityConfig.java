package com.ZamianaRadianow.security.config;

import com.ZamianaRadianow.security.rola.DBRole;
import com.ZamianaRadianow.security.user.DBUser;
import com.ZamianaRadianow.security.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public JwtRequestFilter jwtRequestFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        return new JwtRequestFilter(userDetailsService, jwtUtil);
    }

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


    //=======================


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtRequestFilter jwtRequestFilter) throws Exception {

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .cors().and().csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers(HttpMethod.GET,
                                "/api/games", "/api/games/**",
                                "/api/reviews", "/api/reviews/**",
                                "/api/platforms", "/api/platforms/**",
                                "/api/genres", "/api/genres/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Review endpoints - USER or ADMIN can create/update
                        .requestMatchers(HttpMethod.POST, "/api/reviews").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/reviews/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/reviews/**").hasRole("ADMIN")

                        // Game endpoints - ADMIN only for write operations
                        .requestMatchers(HttpMethod.POST, "/api/games").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/games/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/games/**").hasRole("ADMIN")

                        // Platform endpoints - ADMIN only for write operations
                        .requestMatchers(HttpMethod.POST, "/api/platforms").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/platforms/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/platforms/**").hasRole("ADMIN")

                        // Genre endpoints - ADMIN only for write operations
                        .requestMatchers(HttpMethod.POST, "/api/genres").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/genres/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/genres/**").hasRole("ADMIN")

                        // Admin endpoints
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

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
