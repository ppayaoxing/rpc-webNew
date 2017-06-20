package com.rpc.spring.config.tag;

import java.lang.reflect.Method;

import org.springframework.beans.factory.InitializingBean;

import com.caucho.hessian.server.HessianSkeleton;
import com.rpc.annotation.ReadOnly;
import com.rpc.factory.protocol.ProType;
import com.rpc.http.ServerProtocolProcessor;
import com.rpc.http.hessian.HessianServerProtocalProcessor;
import com.rpc.http.http.HttpServerProtocolProcessor;
import com.rpc.http.jsonRPC.JsonRPCServerProtocolProcessor;
import com.rpc.util.ObjectCacheUtils;
import com.yzmy.jsonrpc4j.JsonRpcMultiServer;

/**
 * 服务端配置
 * @author Administrator
 * @param <T>
 */
public class RpcConfigServer<T> implements InitializingBean {
	
	private String id;//id	
	private String name;//名称	
	private String interfaceClass;//接口	
	private T ref;//引用
	private boolean downgrade = false;//默认 false
	private Object downgradeProcessor;
	private String protocol;//hessian,jsonRPC,http

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
	
	public boolean isDowngrade() {
		return downgrade;
	}

	public void setDowngrade(boolean downgrade) {
		this.downgrade = downgrade;
	}


	public Object getDowngradeProcessor() {
		return downgradeProcessor;
	}

	public void setDowngradeProcessor(Object downgradeProcessor) {
		this.downgradeProcessor = downgradeProcessor;
	}

	
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		ServerProtocolProcessor processor = null;
		if( ProType.HESSIAN.getValue().equals(protocol) ){
			HessianSkeleton skeleton = new HessianSkeleton(ref,Class.forName(interfaceClass));
			processor = new HessianServerProtocalProcessor(skeleton, downgrade, true, downgradeProcessor, this.ref.getClass().getName());
		}else if(ProType.JSONRPC.getValue().equals(protocol)){
			JsonRpcMultiServer server = new JsonRpcMultiServer().addService(Class.forName(interfaceClass).getSimpleName(),ref,Class.forName(interfaceClass));
			processor = new JsonRPCServerProtocolProcessor(server, downgrade, true, downgradeProcessor, this.ref.getClass().getName());
		}else if(ProType.HTTP.getValue().equals(protocol)){
			processor = new HttpServerProtocolProcessor(downgrade, true, downgradeProcessor, this.ref.getClass().getName());
		}		
		ObjectCacheUtils.addHandler(interfaceClass, processor);
		Method[] methods = this.ref.getClass().getDeclaredMethods();
		for(Method method:methods){
			if(method.isAnnotationPresent(ReadOnly.class)){
				ObjectCacheUtils.put(this.ref.getClass().getName(), this.ref.getClass().getName()+"."+method.getName()
				+"."+ObjectCacheUtils.getParameterNames(method.getParameterTypes()));
			}
		}
	}
	
}
