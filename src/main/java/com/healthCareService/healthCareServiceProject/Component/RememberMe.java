package com.healthCareService.healthCareServiceProject.Component;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.healthCareService.healthCareServiceProject.entity.Patient;
import com.healthCareService.healthCareServiceProject.repository.PatientRepo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class RememberMe extends OncePerRequestFilter{
	@Autowired
	private PatientRepo repo;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		if(session!=null && session.getAttribute("userId")!=null) {
			filterChain.doFilter(request, response);
			return;
		}
		
		Cookie[] cookies = request.getCookies();
		
		if(cookies!=null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("remember-me")) {
					String email = cookie.getValue();
					
					Optional<Patient> optional = repo.findByEmail(email);
					
					if(optional.isPresent()) {
						HttpSession newSession = request.getSession(true);
						newSession.setAttribute("userId", optional.get().getPatientid());
					}
				}
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}




















