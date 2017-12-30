package com.himanshu.poc.spring.samples.springbootwebreactive;

import com.himanshu.poc.spring.samples.springbootwebreactive.configurer.InMemDBConfig;
import com.himanshu.poc.spring.samples.springbootwebreactive.configurer.ConfigLoader;
import com.himanshu.poc.spring.samples.springbootwebreactive.domain.User;
import com.himanshu.poc.spring.samples.springbootwebreactive.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ConfigLoader.class, InMemDBConfig.class})
@ActiveProfiles("test")
public class SpringTestProfileConfigLoadTest {
	private static final Logger logger = LoggerFactory.getLogger(SpringTestProfileConfigLoadTest.class);

	@Autowired
	@Qualifier("messageBean")
	private String message;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void shouldLoadConfigFromTestPropertiesIfTestProfileSet() {
		Assert.assertTrue("test profile activated".equalsIgnoreCase(message));
	}

	@Test
	public void testSaveData() {
		User u = new User(null, "alpha", "last", "alpha@rbc.com", true, true, true, true);
		Assert.assertTrue(u.getId() == null);
		User savedU = userRepository.save(u);
		Assert.assertTrue(savedU.getId() != null);
		logger.info(savedU.toString());
	}

}
