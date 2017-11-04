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
package com.himanshu.spring.context.loader.diffconfigpercontext;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class ServiceFactoryDiffConfigPerContext implements ApplicationContextAware, InitializingBean {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private String contextsToLoad = "context1,context2";
	
	private ApplicationContext context;
	
	private Map<String, ConfigurableApplicationContext> appContextMap = new HashMap<String, ConfigurableApplicationContext>();
	
	private ApplicationContext getParentContext() {
		GenericApplicationContext parentContext = new GenericApplicationContext();
		parentContext.getBeanFactory().registerSingleton("dummyBean", this);
		parentContext.getBeanFactory().registerSingleton("mainContext", context);
		parentContext.refresh();
		return parentContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ApplicationContext parentContext = getParentContext();
		String[] contextToLoadArr = contextsToLoad.split(",");
		for (String contextToLoad : contextToLoadArr) {
			if (appContextMap.get(contextToLoad) == null) {
				String beanDefinitionFile = "diff-config-per-context"+"/context/"+contextToLoad+"/"+contextToLoad+"-config.xml";
				logger.info("Loading bean definitions from: "+beanDefinitionFile);
				System.setProperty("context.to.load", contextToLoad);
				ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[]{beanDefinitionFile}, true, parentContext);
				appContextMap.put(contextToLoad, appContext);
			}
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.context = arg0;
	}
	
	public <T> T getBean(Class<T> clazz, String contextStringToLoadFrom) {
		return appContextMap.get(contextStringToLoadFrom).getBean(clazz);
	}

}
