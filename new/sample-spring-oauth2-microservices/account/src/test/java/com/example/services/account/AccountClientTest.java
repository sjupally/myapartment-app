package com.example.services.account;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

import com.example.services.account.model.Account;

public class AccountClientTest {

	@Test
	public void testClient() {
        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setUsername("piomin");
        resourceDetails.setPassword("piot123");
        resourceDetails.setAccessTokenUri("http://localhost:9999/oauth/token");
        resourceDetails.setClientId("account-service");
        resourceDetails.setClientSecret("secret");
        resourceDetails.setGrantType("password");
        resourceDetails.setScope(Arrays.asList("read"));
        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
        restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
        final Account account = restTemplate.getForObject("http://localhost:8082/{id}", Account.class, 1);
        System.out.println(account);
	}
	
}
