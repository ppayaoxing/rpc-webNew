package com.rpc.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.rpc.spring.constant.Constant;

public class RpcCommonRegisterBeanDefinitionParser implements BeanDefinitionParser {
	
	private static Logger logger = LoggerFactory.getLogger(RpcCommonRegisterBeanDefinitionParser.class);
	
	private Class<?> clazz;
	private boolean required;	

	public RpcCommonRegisterBeanDefinitionParser(Class<?> clazz, boolean required) {
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
				name =  clazz.getName();
			}
			id = name;
			if(parserContext.getRegistry().containsBeanDefinition(name)){
				logger.error("parse element id = " + name + " already exsits");
			}
		}
		if(id !=null && id.length()>0){
			parserContext.getRegistry().registerBeanDefinition(id, rootBean);
			rootBean.getPropertyValues().add(Constant.ID, id);
		}		
		rootBean.getPropertyValues().add(Constant.IP,element.getAttribute(Constant.IP));
		rootBean.getPropertyValues().add(Constant.PORT, element.getAttribute(Constant.PORT));
		rootBean.getPropertyValues().add(Constant.WEBAPP, element.getAttribute(Constant.WEBAPP));
		rootBean.getPropertyValues().add(Constant.GROUP, element.getAttribute(Constant.GROUP));
		rootBean.getPropertyValues().add(Constant.ADMINURL, element.getAttribute(Constant.ADMINURL));
		return rootBean;
	}

}