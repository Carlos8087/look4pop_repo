package com.tfg.look4pop.web.app.auth.handler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;

@SpringBootTest
public class LoginSuccessHandlerMockTest {
	
	@Autowired
	private LoginSuccessHandler handler;
	
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	
	@BeforeEach
	void setUp() throws Exception {
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();
	}
	
	// Test login OK
	@DisplayName("login OK")
	@Test
	void testLoginOK() throws Exception {
		Authentication authentication =
		    new TestingAuthenticationToken("admin99", "8080", "ROLE_ADMIN"); 
	    
		handler.onAuthenticationSuccess(request, response, authentication);
		assertThat(response.getRedirectedUrl()).isEqualTo("/");
	}
	
	@AfterEach
	void tearDown() {
		//NA
	}

}
