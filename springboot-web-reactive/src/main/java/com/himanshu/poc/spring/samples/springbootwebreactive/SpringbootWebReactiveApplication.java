package com.himanshu.poc.spring.samples.springbootwebreactive;

import com.himanshu.poc.spring.samples.springbootwebreactive.configurer.ConfigLoader;
import com.himanshu.poc.spring.samples.springbootwebreactive.configurer.InMemDBConfig;
import com.himanshu.poc.spring.samples.springbootwebreactive.controller.v2.RouterConfig;
import com.himanshu.poc.spring.samples.springbootwebreactive.domain.User;
import com.himanshu.poc.spring.samples.springbootwebreactive.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.config.EnableWebFlux;
import sun.tools.jar.CommandLine;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Supplier;

@SpringBootApplication
@Import(value = {ConfigLoader.class, InMemDBConfig.class, RouterConfig.class})
@EnableWebFlux
public class SpringbootWebReactiveApplication {
	private static Logger logger = LoggerFactory.getLogger(SpringbootWebReactiveApplication.class);

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository) {
		return args -> {
			userRepository.deleteAll(); //Cleanup act!
			Arrays.stream(buildUsers(5)).map(userRepository::save).map(User::toString).forEach(logger::info);
		};
	}

	private User[] buildUsers(int size) {
		User[] users = new User[size];
		for (int i=0;i<size;i++) {
			users[i] = this.userSupplier().get();
		}
		return users;
	}

	private Supplier<User> userSupplier() {
		return () -> new User(null, UUID.randomUUID().toString(), UUID.randomUUID().toString()+".last", UUID.randomUUID().toString()+"@"+UUID.randomUUID().toString()+".com", true, true, true, true);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootWebReactiveApplication.class, args);
	}
}
