package com.diagrammingtool.diagrammingtool.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import java.io.IOException;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.diagrammingtool.diagrammingtool.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class JwtAuthenticationFilterTest {

	     @Mock
	    private JwtService jwtService;

	    @Mock
	    private UserDetailsService userDetailsService;

	    @Mock
	    private HandlerExceptionResolver handlerExceptionResolver;

	    @InjectMocks
	    private JwtAuthenticationFilter jwtAuthenticationFilter;

	    private MockMvc mockMvc;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	        mockMvc = MockMvcBuilders.standaloneSetup(jwtAuthenticationFilter)
	                .setHandlerExceptionResolvers(handlerExceptionResolver)
	                .build();
	    }

	    @Test
	    void testDoFilterInternal() throws ServletException,IOException {
	        HttpServletRequest request = mock(HttpServletRequest.class);
	        HttpServletResponse response = mock(HttpServletResponse.class);
	        FilterChain filterChain = mock(FilterChain.class);
	        UserDetails userDetails = new User("testuser@example.com", "password", Collections.emptyList()); // Provide non-empty collection of authorities

	        when(request.getHeader("Authorization")).thenReturn("Bearer mockToken");
	        when(jwtService.extractUsername("mockToken")).thenReturn("testuser@example.com");
	        when(userDetailsService.loadUserByUsername("testuser@example.com")).thenReturn(userDetails);
	        when(jwtService.isTokenValid("mockToken", userDetails)).thenReturn(true);

	        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

	        verify(request, times(1)).getHeader("Authorization");
	        verify(jwtService, times(1)).extractUsername("mockToken");
	        verify(userDetailsService, times(1)).loadUserByUsername("testuser@example.com");
	        verify(jwtService, times(1)).isTokenValid("mockToken", userDetails);
	        verify(filterChain, times(1)).doFilter(request, response);
	        verify(filterChain, times(1)).doFilter(request, response);
	        verify(filterChain, times(1)).doFilter(request, response);
	        verify(filterChain, times(1)).doFilter(request, response);
	        verify(filterChain, times(1)).doFilter(request, response);
	    }
	    
	    @Test
	    void testDoFilterInternal_NoAuthorizationHeader() throws ServletException, IOException {
	        HttpServletRequest request = mock(HttpServletRequest.class);
	        HttpServletResponse response = mock(HttpServletResponse.class);
	        FilterChain filterChain = mock(FilterChain.class);

	        when(request.getHeader("Authorization")).thenReturn(null);

	        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

	        verify(request, times(1)).getHeader("Authorization");
	        verifyNoInteractions(jwtService);
	        verifyNoInteractions(userDetailsService);
	        verify(filterChain, times(1)).doFilter(request, response);
	    }
}
