package com.rpc.spring.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.rpc.spring.config.tag.RpcConfigServer;
import com.rpc.spring.config.tag.RpcConfigClient;

/**
 * @author Administrator
 */
public class RpcCommonNameSpaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("rpcCommonServer", new RpcCommonServerBeanDefinitionParser(RpcConfigServer.class,false));
		registerBeanDefinitionParser("rpcCommonClient", new RpcCommonClientBeanDefinitionParser(RpcConfigClient.class,false));
	}

}
