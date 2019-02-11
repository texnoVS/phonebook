package com.study.phonebook.config;

import com.study.phonebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //Включает проверку пользователя на наличие соответствующей роли
//А конкретно включает аннотацию @PreAuthorize("hasAuthority('...')")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean //Bean необходим, тк необходимо его инжектить в разным местах
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() //Включаем авторизацию
                .antMatchers("/", "/registration", "/static/**", "/activate/*").permitAll() //Разрешение на полный доступ для указанных url и каталогов
                .anyRequest().authenticated() //Для всех остальных запростов требуем авторизацию
                .and()
                .formLogin() //Включаем form login
                .loginPage("/login") //Mapping for login page
                .permitAll() //Разрешаем всем пользоваться
                .and()
                .logout() //Включаем logout
                .permitAll(); //Разрешаем всем пользоваться
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }
}
