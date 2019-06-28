package com.invesco.PDFUtil.configuration;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import waffle.servlet.spi.BasicSecurityFilterProvider;
import waffle.servlet.spi.NegotiateSecurityFilterProvider;
import waffle.servlet.spi.SecurityFilterProvider;
import waffle.servlet.spi.SecurityFilterProviderCollection;
import waffle.spring.NegotiateSecurityFilter;
import waffle.spring.NegotiateSecurityFilterEntryPoint;
import waffle.windows.auth.impl.WindowsAuthProviderImpl;

@Configuration
public class WaffleConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WaffleConfig.class);

    @Bean
    public WindowsAuthProviderImpl waffleWindowsAuthProvider() {
    	LOGGER.info("WaffleConfig:::waffleWindowsAuthProvider:::Method Start End");
        return new WindowsAuthProviderImpl();
    }

    @Bean
    public NegotiateSecurityFilterProvider negotiateSecurityFilterProvider(WindowsAuthProviderImpl windowsAuthProvider) {
    	LOGGER.info("WaffleConfig:::negotiateSecurityFilterProvider:::Method Start");
    	NegotiateSecurityFilterProvider negotiateSecurityFilterProvider = new NegotiateSecurityFilterProvider(windowsAuthProvider);
    	List<String> protocols = new ArrayList<String>();
    	//protocols.add("Negotiate");
    	protocols.add("NTLM");
    	negotiateSecurityFilterProvider.setProtocols(protocols);
    	LOGGER.info("WaffleConfig:::negotiateSecurityFilterProvider:::Method End");
        return negotiateSecurityFilterProvider;
    }

    @Bean
    public BasicSecurityFilterProvider basicSecurityFilterProvider(WindowsAuthProviderImpl windowsAuthProvider) {
    	LOGGER.info("WaffleConfig:::basicSecurityFilterProvider:::Method Start End");
        return new BasicSecurityFilterProvider(windowsAuthProvider);
    }

    @Bean
    public SecurityFilterProviderCollection waffleSecurityFilterProviderCollection(NegotiateSecurityFilterProvider negotiateSecurityFilterProvider, BasicSecurityFilterProvider basicSecurityFilterProvider) {
    	LOGGER.info("WaffleConfig:::waffleSecurityFilterProviderCollection:::Method Start");
        SecurityFilterProvider[] securityFilterProviders = { negotiateSecurityFilterProvider, basicSecurityFilterProvider };
        LOGGER.info("WaffleConfig:::waffleSecurityFilterProviderCollection:::Method End");
        return new SecurityFilterProviderCollection(securityFilterProviders);
    }

    @Bean
    public NegotiateSecurityFilterEntryPoint negotiateSecurityFilterEntryPoint(SecurityFilterProviderCollection securityFilterProviderCollection) {
    	LOGGER.info("WaffleConfig:::negotiateSecurityFilterEntryPoint:::Method Start");
        NegotiateSecurityFilterEntryPoint negotiateSecurityFilterEntryPoint = new NegotiateSecurityFilterEntryPoint();
        negotiateSecurityFilterEntryPoint.setProvider(securityFilterProviderCollection);
        LOGGER.info("WaffleConfig:::negotiateSecurityFilterEntryPoint:::Method End");
        return negotiateSecurityFilterEntryPoint;
    }

    @Bean
    public NegotiateSecurityFilter waffleNegotiateSecurityFilter(SecurityFilterProviderCollection securityFilterProviderCollection) {
    	LOGGER.info("WaffleConfig:::waffleNegotiateSecurityFilter:::Method Start");
        NegotiateSecurityFilter negotiateSecurityFilter = new NegotiateSecurityFilter();
        negotiateSecurityFilter.setProvider(securityFilterProviderCollection);
        LOGGER.info("WaffleConfig:::waffleNegotiateSecurityFilter:::Method End");
        return negotiateSecurityFilter;
    }
    
    // This is required for Spring Boot so it does not register the same filter twice
    @Bean
    public FilterRegistrationBean waffleNegotiateSecurityFilterRegistration(NegotiateSecurityFilter waffleNegotiateSecurityFilter) {
    	LOGGER.info("WaffleConfig:::waffleNegotiateSecurityFilterRegistration:::Method Start");
    	FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    	registrationBean.setFilter(waffleNegotiateSecurityFilter);
    	registrationBean.setEnabled(false);
    	LOGGER.info("WaffleConfig:::waffleNegotiateSecurityFilterRegistration:::Method End");
    	return registrationBean;
    }
    
}
