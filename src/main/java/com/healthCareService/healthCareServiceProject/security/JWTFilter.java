package com.healthCareService.healthCareServiceProject.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {

	@Autowired
	private JWTService jwtService;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {


		String path = request.getServletPath();
		if (path.equals("/doctorController/saveDoctor") || path.equals("/appController/adminLogin")
				|| path.equals("/appController/user_login")) {

			filterChain.doFilter(request, response);
			return;
		}
		String token = null;
		String value = null;
		String actualPath = "";
//		System.out.println(path);
		if(path.startsWith("/adminController")) {
			actualPath = "admin-token";
		}else if(path.startsWith("/userController")) {
			actualPath = "User-token";
		}

		// Read JWT from cookies
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (actualPath.equals(cookie.getName())) {
					token = cookie.getValue();
					break;
				}
			}
		}
		
		if (token != null) {
			try {
				value = jwtService.extractEmail(token);
				if (value != null && SecurityContextHolder.getContext().getAuthentication() == null) {

					UserDetails userDetails = customUserDetailsService.loadUserByUsername(value);

					if (jwtService.validateToken(token, userDetails)) {

						UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());

						authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		filterChain.doFilter(request, response);
	}

}
