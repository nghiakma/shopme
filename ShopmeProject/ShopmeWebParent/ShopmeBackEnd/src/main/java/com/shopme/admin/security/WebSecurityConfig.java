package com.shopme.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	//đc sử dụng trong lớp cấu hình cho biết rằng đối tượng(bean) được quản lý bởi spring container
	//cung cap thong tin nguoi dung
	@Bean
	public UserDetailsService userDetailsService() {
		return new ShopmeUserDetailsService();
	};
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//xác thực trong cơ sở dữ liệu và tìm kiếm người dùng trong cơ sở dữ liệu
	//xác thực tt người dùng dựa trên tt người dùng và mã hóa mật khẩu
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
 
        return authProvider;
    }

	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 http
         .authorizeHttpRequests(authz -> authz
                 .requestMatchers("/images/**", "/js/**", "/webjars/**").permitAll()
                 .requestMatchers("/users/**","/settings/**", "/countries/**", "/states/**").hasAuthority("Admin")
                 .requestMatchers("/categories/**", "/brands/**").hasAnyAuthority("Admin","Editor")
                 .requestMatchers("/products/new", "/products/delete/**").hasAnyAuthority("Admin", "Editor")
     			 .requestMatchers("/products/edit/**", "/products/save", "/products/check_unique")
     				.hasAnyAuthority("Admin", "Editor", "Salesperson")
     			.requestMatchers("/products", "/products/", "/products/detail/**", "/products/page/**")
     				.hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
                 .requestMatchers("/products/**").hasAnyAuthority("Admin", "Editor")
                 .requestMatchers("/customers/**", "/orders/**").hasAnyAuthority("Admin", "Salesperson")
                 .anyRequest().authenticated()
         )
         .formLogin(formLogin -> formLogin
                 .loginPage("/login")
                 .usernameParameter("email")
                 .permitAll()
         )
         .logout(logout -> logout
                 .logoutUrl("/logout")   // specify your logout URL here, if different from default
                 .permitAll()
         ).rememberMe(rememberMe -> rememberMe
                 .key("dfsafhfjhlkjdsjfkdasjf_123132131231123898")// specify your secret key
                 .tokenValiditySeconds(7*24*60*60)  // specify token validity time in seconds
                 .userDetailsService(userDetailsService()) // specify your UserDetailsService here
         );
		 return http.build();
	    }
	
}
