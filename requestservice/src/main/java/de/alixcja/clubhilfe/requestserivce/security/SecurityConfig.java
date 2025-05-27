package de.alixcja.clubhilfe.requestserivce.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Value("${de.alixcja.sso-clubhilfe.requestservice.admin-role}")
  private String ADMIN_ROLE;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.POST, "/ad-requests").permitAll()
                    .requestMatchers("/ad-requests/**").hasRole(ADMIN_ROLE)

                    .requestMatchers(HttpMethod.POST, "/profile-picture-requests").permitAll()
                    .requestMatchers("/profile-picture-requests/**").hasRole(ADMIN_ROLE)

                    .requestMatchers(HttpMethod.GET, "/servers/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/servers").hasRole(ADMIN_ROLE)
                    .requestMatchers(HttpMethod.PUT, "/servers/**").hasRole(ADMIN_ROLE)
                    .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

    return http.build();
  }
}