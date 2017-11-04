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
package com.himanshu.spring.context.loader.sameconfigallcontext.bean.visitor;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PrefixPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	
	private String prefix;
	
	public String getPrefix() {
		return prefix;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
		String[] beansDefinitionNames = beanFactoryToProcess.getBeanDefinitionNames();
		for (String beanDefinitionName : beansDefinitionNames) {
			BeanDefinition beanDefinitionToProcess = beanFactoryToProcess.getBeanDefinition(beanDefinitionName);
			BeanVisitor visitor = new BeanVisitor(prefix, "${", "}");
			visitor.visit(beanDefinitionToProcess);
		}
		
		super.processProperties(beanFactoryToProcess, props);
	}
}
