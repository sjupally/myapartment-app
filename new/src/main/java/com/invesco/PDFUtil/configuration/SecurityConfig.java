package com.invesco.PDFUtil.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import waffle.spring.NegotiateSecurityFilter;
import waffle.spring.NegotiateSecurityFilterEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	private NegotiateSecurityFilter negotiateSecurityFilter;
	
	@Autowired
	private NegotiateSecurityFilterEntryPoint entryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		LOGGER.info("SecurityConfig:::configure HttpSecurity:::Method Start");
		http
			.authorizeRequests()
				.antMatchers("/accessDenied").permitAll()
				.antMatchers("/css/*").permitAll()
				.antMatchers("/js/*").permitAll()
				.antMatchers("/images/*").permitAll()
				.antMatchers("/favicon.ico").permitAll()
				.antMatchers("/**/favicon.ico").permitAll()
				.anyRequest()
				.authenticated()
				.and()
			.httpBasic()
				.authenticationEntryPoint(entryPoint)
				.and()
			.csrf().disable()
			.addFilterBefore(negotiateSecurityFilter, BasicAuthenticationFilter.class)
			.exceptionHandling().accessDeniedPage("/accessDenied");
		LOGGER.info("SecurityConfig:::configure HttpSecurity:::Method End");
	}
	
	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		LOGGER.info("SecurityConfig:::configure AuthenticationManagerBuilder:::Method Start");
		auth.inMemoryAuthentication();
		LOGGER.info("SecurityConfig:::configure AuthenticationManagerBuilder:::Method End");
	}
	
	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		LOGGER.info("SecurityConfig:::configure WebSecurity:::Method Start");
		webSecurity.ignoring().antMatchers("/css/*.css", "/js/*.js","json/*.json","/images/*.jpg","/images/*.png","/favicon.ico", "/**/favicon.ico");
		LOGGER.info("SecurityConfig:::configure WebSecurity:::Method End");
	}

	public NegotiateSecurityFilter getNegotiateSecurityFilter() {
		return negotiateSecurityFilter;
	}

	public void setNegotiateSecurityFilter(NegotiateSecurityFilter negotiateSecurityFilter) {
		this.negotiateSecurityFilter = negotiateSecurityFilter;
	}

	public NegotiateSecurityFilterEntryPoint getEntryPoint() {
		return entryPoint;
	}

	public void setEntryPoint(NegotiateSecurityFilterEntryPoint entryPoint) {
		this.entryPoint = entryPoint;
	}
}
