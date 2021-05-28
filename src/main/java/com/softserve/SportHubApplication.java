package com.softserve;

import com.softserve.app.models.Survey;
import com.softserve.app.models.User;
import com.softserve.app.repository.SportCategoryRepository;
import com.softserve.app.repository.SurveyRepository;
import com.softserve.app.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;


@SpringBootApplication
public class SportHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportHubApplication.class, args);
	}

}
