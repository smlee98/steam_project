package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.demo.dto.RegisterDetail;
import com.example.demo.handler.LoginHandler;
import com.example.demo.service.RegisterService;

@Configuration
@EnableWebSecurity
public class WebSecuriyConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	RegisterService resService;
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/image/**","/thumbnail/**","/upload/**");
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}
	
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new LoginHandler("/main");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable() // 이게 있어야지만 post에 접근이 가능했다...
			.authorizeRequests()
				.antMatchers("/user/**").hasAnyRole("USER,ADMIN,SUPER")
				.antMatchers("/admin/**").hasAnyRole("ADMIN,SUPER")
				.antMatchers("/super/**").hasRole("SUPER")
				.antMatchers("/main","/register").permitAll()
				.antMatchers("/authmail").permitAll()
				.antMatchers("/check").permitAll()
				.antMatchers("/findpw").permitAll()
				.antMatchers("/authpw").permitAll()
				.antMatchers("/search").permitAll()
				.antMatchers("/game").permitAll()
				.antMatchers("/genre").permitAll()
				.antMatchers("/purchaseList").authenticated()
				.antMatchers("/download").authenticated()
				.antMatchers("/fragment/**").permitAll()
				.antMatchers(HttpMethod.POST,"/mypage.**").authenticated()
				.antMatchers(HttpMethod.POST,"/charge.do").authenticated()
				.antMatchers(HttpMethod.POST,"/authpw.**").permitAll()
				.antMatchers(HttpMethod.POST,"/login.**").permitAll()
				.antMatchers(HttpMethod.POST,"/register.**").permitAll()
				.antMatchers(HttpMethod.POST,"/authmail.**").permitAll()
				.antMatchers(HttpMethod.POST,"/admin/upload.**").authenticated()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.usernameParameter("id")
				.passwordParameter("password")
				.defaultSuccessUrl("/main", true)
				.successHandler(successHandler())
				/* .failureHandler("loginFailureHandler") */
				.permitAll()
				.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				.permitAll()
				.invalidateHttpSession(true)
				.and()
			.exceptionHandling()
				.accessDeniedPage("/denied");
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(resService).passwordEncoder(passwordEncoder());
    }
}
