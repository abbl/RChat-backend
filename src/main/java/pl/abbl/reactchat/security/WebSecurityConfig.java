package pl.abbl.reactchat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import pl.abbl.reactchat.security.rest.RestAuthenticationEntryPoint;
import pl.abbl.reactchat.security.rest.RestAuthenticationSuccessHandler;

import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	@Autowired
	RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.exceptionHandling()
		.authenticationEntryPoint(restAuthenticationEntryPoint)
		.and()
		.authorizeRequests()
		.antMatchers("/api").permitAll()
		.antMatchers("/api/secured/**").authenticated()
		.and()
		.formLogin()
		.loginProcessingUrl("/api/authentication")
		.successHandler(restAuthenticationSuccessHandler)
		.failureHandler(new SimpleUrlAuthenticationFailureHandler())
		.and()
		.logout();
	}
	
	//TODO LEFT FOR TEST PURPOSES.
	@Bean
	@Override
	protected UserDetailsService userDetailsService() {
		UserDetails userDetails = User.withDefaultPasswordEncoder()
									.username("test")
									.password("test")
									.roles("USER")
									.build();
		return new InMemoryUserDetailsManager(userDetails);
	}
}
