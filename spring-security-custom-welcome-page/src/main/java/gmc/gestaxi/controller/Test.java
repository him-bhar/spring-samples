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
package gmc.gestaxi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Test {
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String defaultPage(ModelMap map) {
		System.out.println("Inside TEST CLASS");
        return "index";
    }
	
	@RequestMapping(value = "/menuMot", method = RequestMethod.GET)
    public String defaultPageMot(ModelMap map) {
		System.out.println("Inside TEST CLASS MENU MOT");
        return "menuMot";
    }
	
	@RequestMapping(value = "/menuAdmin", method = RequestMethod.GET)
    public String defaultPageAdmin(ModelMap map) {
		System.out.println("Inside TEST CLASS MENU ADMIN");
        return "menuAdmin";
    }
	
	@RequestMapping(value = "/menuUser", method = RequestMethod.GET)
    public String defaultPageUser(ModelMap map) {
		System.out.println("Inside TEST CLASS MENU USER");
        return "menuUser";
    }
}
