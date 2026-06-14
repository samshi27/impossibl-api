package com.impossibl.api.controller;

import com.impossibl.api.dto.LoginRequest;
import com.impossibl.api.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	private static final String COOKIE_NAME = "jwt";
	private static final long COOKIE_MAX_AGE = 24 * 60 * 60; // 24h in seconds

	@PostMapping("/login")
	public ResponseEntity<?> login(
			@Valid @RequestBody LoginRequest request,
			HttpServletResponse response
	) {
		// verify credentials (throws if bad)
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.username(), request.password()));

		// credentials good? then make a token
		String token = jwtUtil.generateToken(authentication.getName());

		// httpOnly cookie
		ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, token)
				.httpOnly(true)
				.secure(false)       // false for localhost (http); true in production (https)
				.path("/")
				.maxAge(COOKIE_MAX_AGE)
				.sameSite("Lax")
				.build();

		response.addHeader("Set-Cookie", cookie.toString());

		return ResponseEntity.ok(Map.of("username", authentication.getName()));
	}

	@GetMapping("/me")
	public ResponseEntity<?> me() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null
				|| !auth.isAuthenticated()
				|| auth instanceof AnonymousAuthenticationToken) {
			return ResponseEntity.status(401).build();
		}

		return ResponseEntity.ok(Map.of("username", auth.getName()));
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletResponse response) {
		ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, "")
				.httpOnly(true)
				.secure(false)      // true in production
				.path("/")
				.maxAge(0)          // expire immediately
				.sameSite("Lax")
				.build();

		response.addHeader("Set-Cookie", cookie.toString());

		return ResponseEntity.ok(Map.of("message", "Logged out"));
	}
}