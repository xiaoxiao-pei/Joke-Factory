package com.PPZ.JokeBar.Controller;

import java.beans.PropertyDescriptor;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;

public class SharedMethod {
	
    public static void copyNotNullFields(Object source, Object target){
    	
    	 final BeanWrapperImpl wrapper = new BeanWrapperImpl(source);
    	 
    	 String[] nullFieldsArray= Stream.of(wrapper.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(propertyName -> wrapper.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    	
        BeanUtils.copyProperties(source, target, nullFieldsArray);
    }

}
