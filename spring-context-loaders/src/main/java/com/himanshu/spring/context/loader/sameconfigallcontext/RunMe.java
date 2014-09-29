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
package com.himanshu.spring.context.loader.sameconfigallcontext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.himanshu.spring.context.common.DummyBean;

public class RunMe {
	private static Logger logger = LoggerFactory.getLogger(RunMe.class);
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:/same-config-all-context/context/applicationContext.xml");
		ServiceFactorySameConfigAllContext svcRepo = appContext.getBean(ServiceFactorySameConfigAllContext.class);
		DummyBean dummyBean_Context1 = svcRepo.getBean(DummyBean.class, "context1");
		logger.info("From Dummy Bean in ctx1 : "+dummyBean_Context1.getWelcomeMsg());
		DummyBean dummyBean_Context2 = svcRepo.getBean(DummyBean.class, "context2");
		logger.info("From Dummy Bean in ctx2 : "+dummyBean_Context2.getWelcomeMsg());
		appContext.destroy();
	}
}
