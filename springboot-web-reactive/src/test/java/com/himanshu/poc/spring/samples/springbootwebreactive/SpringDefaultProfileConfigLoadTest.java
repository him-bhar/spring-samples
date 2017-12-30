package com.himanshu.poc.spring.samples.springbootwebreactive;

import com.himanshu.poc.spring.samples.springbootwebreactive.configurer.ConfigLoader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigLoader.class)
public class SpringDefaultProfileConfigLoadTest {

	@Autowired
	@Qualifier("messageBean")
	private String message;

	@Test
	public void shouldLoadConfigFromDefaultPropertiesIfNotProfileSet() {
		Assert.assertTrue("reading from default properties".equalsIgnoreCase(message));
	}

}
