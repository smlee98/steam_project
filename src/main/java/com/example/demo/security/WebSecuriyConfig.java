package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.service.RegisterService;

@Configuration
@EnableWebSecurity
public class WebSecuriyConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	RegisterService resService;
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/image/**");
    }
	
	@Bean
		public PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable() // 이게 있어야지만 post에 접근이 가능했다...
			.authorizeRequests()
				.antMatchers("/main","/register").permitAll()
				.antMatchers(HttpMethod.POST,"/register.**").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.usernameParameter("id")
				.passwordParameter("password")
				.defaultSuccessUrl("/main", true)
				.permitAll()
				.and()
			.logout()
				.logoutSuccessUrl("/main")
				.permitAll()
				.invalidateHttpSession(true);
	}
}
