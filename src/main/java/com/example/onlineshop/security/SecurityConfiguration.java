package com.example.onlineshop.security;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/users/auth").fullyAuthenticated()
                .antMatchers("/").fullyAuthenticated()
                .antMatchers("/users/*").permitAll()
                .antMatchers("/users/*/*").permitAll()
                .antMatchers("/guests").permitAll()
                .antMatchers("/users/register/post").permitAll()
                .antMatchers("/basket/**").hasAnyRole("GUEST", "USER", "ADMIN")
                .antMatchers("/products/*/*").hasAnyRole("GUEST", "USER", "ADMIN")
                .antMatchers("/products/*").hasAnyRole("GUEST", "USER", "ADMIN")
                .antMatchers("/products/reviews").hasAnyRole("USER", "ADMIN")
                .antMatchers("/static/**/**").permitAll()
                .antMatchers( "/users/registration").permitAll()
                .antMatchers("/captcha/**").permitAll()
                .anyRequest().fullyAuthenticated();

        http.formLogin()
                .loginPage("/users/login")
                .successHandler(customSuccessHandler())
                .loginProcessingUrl("/users/auth")
                .failureUrl("/users/unsuccessfulLoginEntry")
                .usernameParameter("email")
                .permitAll();


        http.logout().logoutUrl("/users/logout")
                .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
        http.csrf().csrfTokenRepository(new HttpSessionCsrfTokenRepository())
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/secured/*"));
//        http.sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new CustomSuccessHandler();
    }


    private static class CustomSuccessHandler implements AuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            response.sendRedirect("/users/auth");
        }
    }

    private static class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            response.sendRedirect("/login?logout");
        }
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return new HttpSessionCsrfTokenRepository();
    }
}
