package com.softserve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SportHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportHubApplication.class, args);
	}
/*
	@Bean
	CommandLineRunner init(UserRepository userRepository, SurveyRepository surveyRepository) {
		return args -> {
			Stream.of("Anna").forEach(name -> {

				User u = new User();
				u.setUsername(name);
				u.setEmail(name + "@mail.ml");
				u.setPassword(name);
				userRepository.save(u);

				Survey s = new Survey();
				s.setUser(u);
				s.setQuestion("Who is " + u.getUsername() + "?");
				surveyRepository.save(s);

				Survey s2 = new Survey();
				s2.setUser(u);
				s2.setQuestion("Where is " + u.getUsername() + "?");
				surveyRepository.save(s2);
			});
		};
	}

 */
}
