package com.shopme.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.shopme.security.oauth.CustomerOAuth2UserService;
import com.shopme.security.oauth.OAuth2LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired private CustomerOAuth2UserService oAuth2UserService;
	@Autowired private OAuth2LoginSuccessHandler oauth2LoginHandler;
	@Autowired private DatabaseLoginSuccessHandler databaseLoginHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 http
         .authorizeHttpRequests(authz -> authz
                 .requestMatchers("/images/**", "/js/**", "/webjars/**").permitAll()
                 .requestMatchers("/account_details", "/update_account_details", 
     					"/cart", "/address_book/**", "/checkout", "/place_order").authenticated()
                 .anyRequest().permitAll()
         )
         .formLogin(formLogin -> formLogin
                 .loginPage("/login")
                 .usernameParameter("email")
                 .successHandler(databaseLoginHandler)
                 .permitAll()
         ).oauth2Login(oauth2 -> oauth2
        		 .loginPage("/login")
        		  .userInfoEndpoint(userInfo -> userInfo
        				  .userService(oAuth2UserService)
         ).successHandler(oauth2LoginHandler)
         )
         .logout(logout -> logout
                 .logoutUrl("/logout")   // specify your logout URL here, if different from default
                 .permitAll()
         ).rememberMe(rememberMe -> rememberMe
                 .key("dfsafhfjhlkjdsjfkdasjf_123132131231123898")// specify your secret key
                 .tokenValiditySeconds(7*24*60*60)  // specify token validity time in seconds
                 
         ).sessionManagement(sss -> sss
        		 .sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
		 return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomerUserDetailsService();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}	
}
