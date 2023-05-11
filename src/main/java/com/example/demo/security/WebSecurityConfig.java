package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.AllArgsConstructor;
 
@AllArgsConstructor
@Configuration
public class WebSecurityConfig {
	private   UserDetailsService userDetailsService  ;
	private  JWTAuthenticationFilter jwtAuthenticationFilter  ;
	
	@Bean // Anotación para indicar que este método es un bean de Spring

	SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager autManager) throws Exception {
		 JWTAuthenticationFilter jwtAuthorizationFilter = new JWTAuthenticationFilter();
		 jwtAuthorizationFilter.setAuthenticationManager(autManager);
		 jwtAuthorizationFilter.setFilterProcessesUrl("/login");
		// Método que crea una cadena de filtros de seguridad para una aplicación web.
		// Recibe como parámetros un objeto HttpSecurity y un objeto
		// AuthenticationManager.
		return http // Devuelve un objeto HttpSecurity
				.csrf().disable() // Deshabilita la protección CSRF (Cross-Site Request Forgery)
				.authorizeHttpRequests() // Configura la autorización de las solicitudes HTTP
				.anyRequest() // Cualquier petición
				.authenticated() // Debe estar autenticada
				.and().httpBasic() // Usa autenticación HTTP básica
				.and().sessionManagement() // Configura la gestión de la sesión
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Establece la política de creación de sesión como STATELESS (sin estado)
				.and()
				.addFilter(jwtAuthorizationFilter)
				.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
				.build(); // Crea y devuelve la cadena de filtros de seguridad
	}

//	@Bean
//	UserDetailsService userDetailsService() {
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		manager.createUser(User.withUsername("admin").password(passwordEncoder().encode("admin")).roles().build());
//		return manager;
//	}

	@Bean
	AuthenticationManager auhtManager(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder()).and().build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}