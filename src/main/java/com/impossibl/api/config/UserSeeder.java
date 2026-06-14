package com.impossibl.api.config;

import com.impossibl.api.entity.User;
import com.impossibl.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSeeder implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Value("${SEED_PASSWORD_SAMSHI}")
	private String samshiPassword;

	@Value("${SEED_PASSWORD_POOJI}")
	private String poojiPassword;

	@Value("${SEED_PASSWORD_PHANI}")
	private String phaniPassword;

	@Override
	public void run(String... args) {
		if (userRepository.count() > 0) {
			log.info("Users already seeded, skipping.");
			return;
		}
		userRepository.save(User.builder()
				.username("samshi").password(passwordEncoder.encode(samshiPassword))
				.displayName("Samshi").build());
		userRepository.save(User.builder()
				.username("pooji").password(passwordEncoder.encode(poojiPassword))
				.displayName("Pooji").build());
		userRepository.save(User.builder()
				.username("phani").password(passwordEncoder.encode(phaniPassword))
				.displayName("Phani").build());
		log.info("Seeded {} users.", userRepository.count());
	}
}