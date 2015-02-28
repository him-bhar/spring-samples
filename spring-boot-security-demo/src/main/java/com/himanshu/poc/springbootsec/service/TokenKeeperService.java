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
package com.himanshu.poc.springbootsec.service;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

@Component
public class TokenKeeperService {
  //Key username and Value is token
  private BiMap<String, String> userTokenMap =  HashBiMap.<String, String>create();
  
  public String generateNewToken(String username) {
    if (! userTokenMap.containsKey(username)) {
      String token = UUID.randomUUID().toString();
      userTokenMap.put(username, token);
    }
    return userTokenMap.get(username);
  }
  
  public String queryUserByToken(String token) {
    //Map inversed now the token is key and value is username
    return userTokenMap.inverse().get(token);
  }
  
}
