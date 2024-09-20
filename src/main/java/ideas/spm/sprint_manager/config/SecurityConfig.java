package ideas.spm.sprint_manager.config;

import ideas.spm.sprint_manager.filters.JwtRequestFilter;
import ideas.spm.sprint_manager.roles.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                        .disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/h2-console/**").permitAll()
                        .requestMatchers("/admin/**").hasRole(Roles.ROLE_ADMIN)
                        .requestMatchers("/manager/**").hasAnyRole(Roles.ROLE_MANAGER,Roles.ROLE_ADMIN)
//                        .requestMatchers("/team/**").hasAnyRole(Roles.ROLE_MANAGER,Roles.ROLE_ADMIN)
                        .requestMatchers("/task/manager/**").hasAnyRole(Roles.ROLE_MANAGER,Roles.ROLE_ADMIN)
                        .requestMatchers("/task/employee/**").hasAnyRole(Roles.ROLE_EMPLOYEE,Roles.ROLE_MANAGER,Roles.ROLE_ADMIN)
                        .requestMatchers("/employee/**").hasAnyRole(Roles.ROLE_EMPLOYEE,Roles.ROLE_MANAGER,Roles.ROLE_ADMIN)
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}