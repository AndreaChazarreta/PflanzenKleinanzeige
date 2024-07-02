package com.sopra.pflanzenkleinanzeigen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        // define all URLs which should be accessible without login
                        auth -> auth
                                .requestMatchers("/register", "/login", "/plant-images/**").permitAll()
                                // define all URLs which require an authenticated user with a certain role
                                // NOTE: Spring Security automatically adds "ROLE_" while performing this check. For this reason we do not
                                // have to use "ROLE_ADMIN" here, which we define in the TestDatabaseLoader.
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                // all other URLs (except the ones above) require authentication too
                                .anyRequest().authenticated())
                // include CSRF token, which may be required while performing AJAX-requests
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers("/console/**"))
                // define the login-page, which is also accessible for everyone

                .formLogin( formLogin -> formLogin
                        .loginPage("/login").failureUrl("/login?error=true").permitAll()
                        .defaultSuccessUrl("/plants", true)
                        .usernameParameter("username")
                        .passwordParameter("password"))
                // everyone may logout

                .logout(logout -> logout.permitAll()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout"))

                //Disables header security. This allows the use of the h2 console.
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                // gewähre immer Zugriff auf Dateien in den folgenden Ordnern
                .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/plant-images/**");
    }

    /**
     * Password-encoder Bean der Spring Security. Wird zum Verschlüsseln von Passwörtern benötigt.
     *
     * @return BCryptPasswordEncoder bean.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}