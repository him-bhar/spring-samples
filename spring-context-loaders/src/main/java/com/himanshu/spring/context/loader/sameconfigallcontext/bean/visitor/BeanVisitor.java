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

import java.util.Collection;
import java.util.List;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.ConstructorArgumentValues.ValueHolder;
import org.springframework.beans.factory.config.TypedStringValue;

public class BeanVisitor {
	
	private String prefix;
	private String propertyReferPrefixValueStart;
	private String propertyReferPrefixValueEnd;
	
	
	public BeanVisitor(String prefix, String propertyReferPrefixValueStart, String propertyReferPrefixValueEnd) {
		this.prefix = prefix;
		this.propertyReferPrefixValueStart = propertyReferPrefixValueStart;
		this.propertyReferPrefixValueEnd = propertyReferPrefixValueEnd;
	}
	
	public void visit(BeanDefinition beanDefinition) {
		visitProperties(beanDefinition.getPropertyValues());
		visitGenericConstructorArgs(beanDefinition.getConstructorArgumentValues().getGenericArgumentValues());
		visitIndexedConstructorArgs(beanDefinition.getConstructorArgumentValues().getIndexedArgumentValues().values());
	}

	private void visitIndexedConstructorArgs(Collection<ValueHolder> values) {
		for (ConstructorArgumentValues.ValueHolder valueHolder : values) {
			Object resolvedValueHolder = resolveValue(valueHolder.getValue());
			valueHolder.setValue(resolvedValueHolder);
		}
	}

	private void visitGenericConstructorArgs(List<ValueHolder> genericArgumentValues) {
		for (ConstructorArgumentValues.ValueHolder valueHolder : genericArgumentValues) {
			Object resolvedValueHolder = resolveValue(valueHolder.getValue());
			valueHolder.setValue(resolvedValueHolder);
		}
	}

	private void visitProperties(MutablePropertyValues propertyValues) {
		for (PropertyValue propertyValue : propertyValues.getPropertyValueList()) {
			Object resolvedValue = resolveValue(propertyValue.getValue());
			propertyValues.add(propertyValue.getName(), resolvedValue);
		}
	}

	private Object resolveValue(Object value) {
		if (value instanceof BeanDefinition) {
			this.visit((BeanDefinition)value);
		} else if (value instanceof BeanDefinitionHolder) {
			this.visit(((BeanDefinitionHolder)value).getBeanDefinition());
		} else if (value instanceof TypedStringValue) {
			value = insertPrefixToProperty((TypedStringValue)value);
		} else if (value instanceof String) {
			value = insertPrefixToProperty((String)value);
		}
		return value;
	}

	private String insertPrefixToProperty(String propertyValue) {
		if (propertyValue != null && propertyValue.startsWith(propertyReferPrefixValueStart)) {
			StringBuilder resolvedPropertyValue = new StringBuilder(propertyReferPrefixValueStart);
			resolvedPropertyValue.append(prefix);
			resolvedPropertyValue.append(".");
			resolvedPropertyValue.append(propertyValue.substring(2));	// for : ${
			return resolvedPropertyValue.toString();
		}
		return propertyValue;
		
	}

	private TypedStringValue insertPrefixToProperty(TypedStringValue typedStringValue) {
		return new TypedStringValue(insertPrefixToProperty(typedStringValue.getValue()));
		
	}

}
