package com.rpc.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.rpc.spring.constant.Constant;

/**
 * 客户端标签解析
 * @author Administrator
 */
public class RpcCommonClientBeanDefinitionParser implements BeanDefinitionParser {
	
private static Logger logger = LoggerFactory.getLogger(RpcCommonClientBeanDefinitionParser.class);
	
	private Class<?> clazz;
	private boolean required;	

	public RpcCommonClientBeanDefinitionParser(Class<?> clazz, boolean required) {
		super();
		this.clazz = clazz;
		this.required = required;
	}

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		return parse(element,parserContext,clazz,required);
	}

	private BeanDefinition parse(Element element, ParserContext parserContext, Class<?> clazz2, boolean required2) {
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
		rootBean.getPropertyValues().add(Constant.URL, element.getAttribute(Constant.URL));
		rootBean.getPropertyValues().add(Constant.TIMEOUT, element.getAttribute(Constant.TIMEOUT));
		rootBean.getPropertyValues().add(Constant.PROTOCOL, element.getAttribute(Constant.PROTOCOL));
		rootBean.getPropertyValues().add(Constant.CLUSTER, element.getAttribute(Constant.CLUSTER));
		rootBean.getPropertyValues().add(Constant.BALANCE, element.getAttribute(Constant.BALANCE));
		return rootBean;
	}

}
