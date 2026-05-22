package com.healthCareService.healthCareServiceProject.configuration;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.healthCareService.healthCareServiceProject.entity.Admin;
import com.healthCareService.healthCareServiceProject.entity.Patient;

public class CustomUserDetails implements UserDetails {

	private Patient patient;
	
	private Admin admin;

	public CustomUserDetails(Patient patient) {
		this.patient = patient;
	}
	
	public CustomUserDetails(Admin admin) {
		this.admin = admin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(admin != null) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		if(admin!=null) {
			return admin.getPassword();
		}
		return patient.getPassword();
	}

	@Override
	public String getUsername() {
		if(admin!=null) {
			return admin.getAdminid();
		}
		return patient.getPatientid();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
