package com.aap.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    private static final String LOGIN_URL = "/login";

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers(LOGIN_URL,"/register","/clubs","/css/**","/js/**")
                .permitAll()
                .and()
                .formLogin(form -> form
                        .loginPage(LOGIN_URL)
                        .defaultSuccessUrl("/clubs")
                        .loginProcessingUrl(LOGIN_URL)
                        .failureUrl("/login?error=true")
                        .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll());
        return httpSecurity.build();
    }

    public void configure(AuthenticationManagerBuilder managerBuilder) throws Exception{
        managerBuilder.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }
}
