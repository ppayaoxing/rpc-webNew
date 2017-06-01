package com.rpc.spring.config.tag;

import org.springframework.beans.factory.InitializingBean;

import com.caucho.hessian.server.HessianSkeleton;
import com.rpc.http.HessianDispatcher;

/**
 * 服务端配置
 * @author Administrator
 * @param <T>
 */
public class RpcConfig<T> implements InitializingBean {
	
	private String id;//id	
	private String name;//名称	
	private String interfaceClass;//接口	
	private T ref;//引用

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public T getRef() {
		return ref;
	}

	public void setRef(T ref) {
		this.ref = ref;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInterfaceClass() {
		return interfaceClass;
	}

	public void setInterfaceClass(String interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		HessianSkeleton skeleton = new HessianSkeleton(ref,Class.forName(interfaceClass));
		HessianDispatcher.addHandler(interfaceClass, skeleton);
	}
	
}
