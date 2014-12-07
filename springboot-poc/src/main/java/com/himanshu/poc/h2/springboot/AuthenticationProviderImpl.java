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

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {
	
	private Map<String, String> dummyUsernamePwdMap = new HashMap<String, String>();
	
	{
		dummyUsernamePwdMap.put("user", "user");
		dummyUsernamePwdMap.put("admin", "admin");
		dummyUsernamePwdMap.put("himanshu", "bhardwaj");
	}

	@Override
	public Authentication authenticate(Authentication arg0)
			throws AuthenticationException {
		System.out.println(" User name is : "+arg0.getName());
		//arg0.setAuthenticated(false);
		//return arg0;
		if (dummyUsernamePwdMap.get(arg0.getPrincipal()) != null 
				&& dummyUsernamePwdMap.get(arg0.getPrincipal()).equals(arg0.getCredentials())) {
			System.out.println("Auth success");
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(arg0.getPrincipal(), arg0.getCredentials(), arg0.getAuthorities());
			return token;
		}
		System.out.println("Auth failed");
		return null;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}
