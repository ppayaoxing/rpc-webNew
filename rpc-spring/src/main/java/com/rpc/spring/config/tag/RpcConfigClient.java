package com.rpc.spring.config.tag;

import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ClassUtils;

import com.rpc.factory.BalanceFactory;
import com.rpc.factory.ClusterFactory;
import com.rpc.factory.ProtocolFactory;
import com.rpc.factory.ProxyFactory;
import com.rpc.factory.handler.Handler;
import com.rpc.factory.handler.JdkHandler;

/**
 * 客户端配置
 * @author Administrator
 *
 */
public class RpcConfigClient implements FactoryBean<Object>,InitializingBean {
	
	private Logger logger = LoggerFactory.getLogger(RpcConfigClient.class);
	private String id;//id	
	private String name;//名称	
	private String interfaceClass;//接口	
	private String url;//地址	
	private Object proxyObj = null;
	
	private String cluster="failover";//请求机制
	private String balance="round";//负载均衡
	private String protocol="hessian";//
	
	private ProxyFactory proxyFactory = ProxyFactory.getProxyFactory();//代理工厂
	private ClusterFactory clusterFactory =  ClusterFactory.getClusterFactory();//请求机制
	private ProtocolFactory protocolFactory = ProtocolFactory.getProtocolFactory();//协议
	private BalanceFactory balanceFactory = BalanceFactory.getBalanceFactory();//负载均衡
	private int timeout = 10*60*1000;

	
	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public Object getObject() throws Exception {
		if(proxyObj == null)
			createProxy();	
		return proxyObj;
	}

	/**
	 * 创建代理对象
	 */
	private void createProxy() {
		Handler handler;
		try {
			handler = new JdkHandler(clusterFactory.getCluster(this.cluster), 
					protocolFactory.getProtocol(protocol), getObjectType(), this.url,balanceFactory.getBalance(balance),this.timeout,this.interfaceClass);
			this.proxyObj = Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), new Class<?>[]{getObjectType()},
					proxyFactory.getProxy(handler));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Class<?> getObjectType() {
		try {
			return Class.forName(interfaceClass);
		} catch (ClassNotFoundException e) {
			logger.error("",e);
		}
		return null;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
////		this.proxy =Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), new Class[]{Class.forName(interfaceClass)}, new HessianHandler());
//	    HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
//        hessianProxyFactory.setConnectTimeout(10000);
//        hessianProxyFactory.setReadTimeout(10000);
//        Class<?> clazz = Class.forName(interfaceClass);
//        this.proxyObj =  hessianProxyFactory.create(clazz, url, Thread.currentThread().getContextClassLoader());
	}

}
