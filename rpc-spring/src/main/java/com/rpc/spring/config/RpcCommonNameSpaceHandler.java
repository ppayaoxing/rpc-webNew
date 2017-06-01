package com.rpc.spring.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.rpc.spring.config.tag.RpcConfig;
import com.rpc.spring.config.tag.RpcConfigClient;

public class RpcCommonNameSpaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("rpcCommonServer", new RpcCommonBeanDefinitionParser(RpcConfig.class,false));
		registerBeanDefinitionParser("rpcCommonClient", new RpcCommonClientBeanDefinitionParser(RpcConfigClient.class,false));
	}

}
