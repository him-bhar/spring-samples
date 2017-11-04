/*
 * Copyright 2013 Himanshu Bhardwaj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.himanshu.poc.h2.springboot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleWebStarter.class)
@WebAppConfiguration
@IntegrationTest(value="server.port:0")
public class H2SpringBootTestIT {
	private Logger logger = LoggerFactory.getLogger(H2SpringBootTestIT.class);
	
	@Value("${local.server.port}")
  private int port;
	
	private String url = null;
	
	@Before
	public void setup() {
	  url = "http://localhost:"+this.port;
	}
	
	
	@Test
	public void testSimpleGet() {
	  HttpEntity<String> response = new TestRestTemplate().getForEntity(url.concat("/sample/echo/all"), String.class);
	  logger.info("Response is :->"+response);
	}
}
