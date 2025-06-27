package com.garganttua.noghost.backend.api.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.garganttua.api.spec.security.IGGAPIPasswordEncoder;

@Service
public class BcryptPasswordEncoder implements IGGAPIPasswordEncoder {

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	@Override
	public String encode(String password) {
		return this.bCryptPasswordEncoder.encode(password);
	}

	@Override
	public boolean matches(String rawPassword, String encodedPassword) {
		return this.bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
	}

}
