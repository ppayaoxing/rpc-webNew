package com.rpc.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.rpc.spring.constant.Constant;

public class RpcCommonBeanDefinitionParser implements BeanDefinitionParser {
	
	private static Logger logger = LoggerFactory.getLogger(RpcCommonBeanDefinitionParser.class);
	
	private Class<?> clazz;
	private boolean required;	

	public RpcCommonBeanDefinitionParser(Class<?> clazz, boolean required) {
		super();
		this.clazz = clazz;
		this.required = required;
	}

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		return parse(element, parserContext,clazz,required);
	}

	private BeanDefinition parse(Element element, ParserContext parserContext,Class<?> clazz, boolean required2) {
		RootBeanDefinition rootBean = new RootBeanDefinition();
		rootBean.setBeanClass(clazz);
		rootBean.setLazyInit(false);
		String id = element.getAttribute("id");
		if(id == null ||"".equals(id)){
			String name = element.getAttribute("name");
			if(name == null || "".equals(name)){
				name =  element.getAttribute("interfaceClass");
			}
			id = name;
			if(parserContext.getRegistry().containsBeanDefinition(name)){
				logger.error("parse element id = "+name+" already exsits");
			}
		}
		if(id !=null && id.length()>0){
			parserContext.getRegistry().registerBeanDefinition(id, rootBean);
			rootBean.getPropertyValues().add(Constant.ID, id);
		}		
		rootBean.getPropertyValues().add(Constant.NAME, element.getAttribute(Constant.NAME));
		rootBean.getPropertyValues().add(Constant.INTERFACECLASS, element.getAttribute(Constant.INTERFACECLASS));
		String refVal = element.getAttribute(Constant.REF);
		if(refVal!=null){
			BeanDefinition bean = parserContext.getRegistry().getBeanDefinition(refVal);
			if(!bean.isSingleton()){
				logger.error(" ref bean must be singleton");
			}
			rootBean.getPropertyValues().add(Constant.REF,  new RuntimeBeanReference(refVal));
		}
		return rootBean;
	}

}
