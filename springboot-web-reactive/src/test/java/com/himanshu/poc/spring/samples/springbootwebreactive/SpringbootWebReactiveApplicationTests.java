package com.himanshu.poc.spring.samples.springbootwebreactive;

import com.himanshu.poc.spring.samples.springbootwebreactive.configurer.InMemDBConfig;
import com.himanshu.poc.spring.samples.springbootwebreactive.configurer.ConfigLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ConfigLoader.class, InMemDBConfig.class})
public class SpringbootWebReactiveApplicationTests {

	@Test
	public void contextLoads() {
	}

}
