package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {

	@Test
	public void testEncodePassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPasswordString = "name2020";
		String encodedPassword = passwordEncoder.encode(rawPasswordString);
		
		System.out.println(encodedPassword);
		
		boolean matches = passwordEncoder.matches(rawPasswordString, encodedPassword);
		
		assertThat(matches).isTrue();
	}
}
