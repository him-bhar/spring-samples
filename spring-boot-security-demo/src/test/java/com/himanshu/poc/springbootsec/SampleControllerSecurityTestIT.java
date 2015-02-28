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
package com.himanshu.poc.springbootsec;

import org.apache.commons.codec.binary.Base64;
import org.hamcrest.Matchers;
import org.junit.Assert;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.himanshu.poc.springbootsec.starter.SampleWebStarter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleWebStarter.class)
@WebAppConfiguration
@IntegrationTest(value="server.port:0")
public class SampleControllerSecurityTestIT {
	private Logger logger = LoggerFactory.getLogger(SampleControllerSecurityTestIT.class);
	
	@Value("${local.server.port}")
  private int port;
	
	private String url = null;
	
	@Before
	public void setup() {
	  url = "http://localhost:"+this.port;
	}
	
	@Test
	public void testSecureGetWithUserAndPass() {
	  HttpEntity<String> response = new TestRestTemplate("Himanshu", "Bhardwaj").getForEntity(url.concat("/secure/sample/test"), String.class);
	  logger.info("Response is :->"+response);
	}
	
	@Test
  public void testSecureGetWithToken() {
    ResponseEntity<String> response = new TestRestTemplate("Himanshu", "Bhardwaj").getForEntity(url.concat("/secure/generate/token/Himanshu"), String.class);
    logger.info("Response is :->"+response);
    String tokenReceived = response.getBody();
    Assert.assertThat(response.getStatusCode(), Matchers.equalTo(HttpStatus.OK));
    
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic ".concat(generateAuthorizationToken(tokenReceived)));
    
    HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
    
    ResponseEntity<String> response2 = new TestRestTemplate().exchange(url.concat("/secure/sample/test"), HttpMethod.GET, requestEntity, String.class);
    logger.info("Response2 is :->"+response2);
    Assert.assertThat(response2.getStatusCode(), Matchers.equalTo(HttpStatus.OK));
    
    ResponseEntity<String> response3 = new TestRestTemplate().exchange(url.concat("/secure/sample/test/forbidden"), HttpMethod.GET, requestEntity, String.class);
    logger.info("Response3 is :->"+response3);
    Assert.assertThat(response3.getStatusCode(), Matchers.equalTo(HttpStatus.FORBIDDEN));
    
  }

  private String generateAuthorizationToken(String tokenReceived) {
    String tokenStrToEncode = ":".concat(tokenReceived);
    return new String(Base64.encodeBase64(tokenStrToEncode.getBytes()));
  }
}
