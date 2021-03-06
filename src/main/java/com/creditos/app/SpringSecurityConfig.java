package com.creditos.app;


import com.creditos.app.auth.handler.LoginSuccessHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration

public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
         private LoginSuccessHandler successHandler;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       
      http.authorizeRequests().antMatchers("/","/css/**","/js/**","/images/**")
      .permitAll()
      .antMatchers("/clientes/**").hasAnyRole("USER")
      .anyRequest().authenticated()
      .and()
        .formLogin()
        .successHandler(successHandler)
        .loginPage("/login")
        .permitAll()
      .and()
      .logout().permitAll()
      .and()
      .exceptionHandling().accessDeniedPage("/error_403");
     
    }
    @Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder builder)throws Exception{
       PasswordEncoder encoder = passwordEncoder();
        UserBuilder users = User.builder().passwordEncoder(encoder :: encode);
        builder.inMemoryAuthentication()
        .withUser(users.username("deimer").password("12345").roles("USER"))
        .withUser(users.username("admin").password("12345").roles("ADMIN","USER"));
    }
}
