package com.starsoft1.bms.config;


import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.starsoft1.bms.dao.UserDAO;
import com.starsoft1.bms.model.UserModel;

@Configuration
@EnableMethodSecurity
public class ProjectSecurityConfig  {

	private UserDAO UserDAO;




	public ProjectSecurityConfig(UserDAO UserDAO) {
		this.UserDAO = UserDAO;
	}
	
	@Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests()
		.requestMatchers("/").authenticated()
		.requestMatchers("/adminMypage").hasAnyAuthority("ROLE_管理者")
		.requestMatchers("/createrMypage").hasAnyAuthority("ROLE_ユーザー")
		.requestMatchers("/editorMypage").hasAnyAuthority("ROLE_承認者")
		.requestMatchers("/userMypage").hasAnyAuthority("ROLE_閲覧ユーザー")
		.requestMatchers("/**").permitAll()
		//.requestMatchers("/").hasAnyAuthority("ROLE_")
		.and().formLogin()
		.loginPage("/login").usernameParameter("email")
		.failureHandler(authenticationFailureHandler())
		//.defaultSuccessUrl("/").failureUrl("/login?error=true").permitAll()
		.and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll()
		.and().httpBasic();

		return http.build();
	}
	
	

	//    @Bean
	//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	//    	return http
	//    	        .csrf(csrf -> csrf.disable())
	//    	        .authorizeHttpRequests(auth -> auth
	//    	            .requestMatchers("/login").permitAll()
	//    	            .anyRequest().authenticated()
	//    	            .requestMatchers("/admin").hasRole("ADMIN") 
	//    	            .requestMatchers("/Mypage").hasRole("ADMIN") 
	//    	        )
	//    	        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	//    	        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
	//    	        .httpBasic(Customizer.withDefaults())
	//    	        .build();
	//    }



	@Bean
	public InMemoryUserDetailsManager userDetailsService() {

		ArrayList<UserDetails> userlist = new ArrayList<>();
		ArrayList<UserModel> users = UserDAO.getAllUsers();

		for(UserModel userEntity: users) {
			UserDetails customer = User.withDefaultPasswordEncoder()
					.username(userEntity.getUserEmail())
					.password(userEntity.getUserPassword())
					.roles(userEntity.getUserRole())
					.build();
			userlist.add(customer);

		}

		return new InMemoryUserDetailsManager(userlist);
	}

}

