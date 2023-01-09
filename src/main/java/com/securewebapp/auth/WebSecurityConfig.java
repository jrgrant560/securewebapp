package com.securewebapp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter; DEPRECATED
import org.springframework.security.web.SecurityFilterChain; //NEW RECOMENDATION instead of WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//import com.securewebapp.auth.MySQLUserDetailsService;  import not needed???

//configures how authorization will be handled by the application
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private MySQLUserDetailsService mySQLUserDetailsService; //PROBLEM: circular dependency: the service also depends on this config to encode its passwords when saving users

	// @Autowired
    // public WebSecurityConfig(MySQLUserDetailsService mySQLUserDetailsService) {
    //     this.mySQLUserDetailsService = mySQLUserDetailsService;
    // }

	// password encoder that uses bcrypt
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//defines the details service and password encoder from AuthenticationManagerBuilder
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.userDetailsService(mySQLUserDetailsService)
				.passwordEncoder(passwordEncoder());
	}

	// OBSOLETE CODE due to WebSecurityConfigurerAdapter being deprecated; updated to next SecurityFilterChain objecgt instead
	// @Override
	// protected void configure(HttpSecurity http) throws Exception {
	// 	http
	// 		.authorizeRequests()
	// 		.antMatchers("/", "/home", "/register").permitAll()
	// 		.anyRequest()
	// 		.authenticated()
	// 		.and()
	// 		.formLogin()
	// 		.loginPage("/login")
	// 		.permitAll()
	// 		.and()
	// 		.logout()
	// 		.permitAll();
	// }

	// UPDATED version of WebSecurityConfigurerAdapter; may need to make adjustments to code that uses this method
	// specifies which paths are accessible to everyone and which paths are restricted
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {

		http
				.authorizeHttpRequests()
				.requestMatchers("/", "/home", "/register").permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
				.logout()
				.permitAll();

		return http.build();
	}
}