package com.rpc.factory.protocol.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.HttpStatus;

import com.rpc.factory.ParamsData;

public class HttpFactory {

	private ParamsData params;

	public HttpFactory(ParamsData params) {
		super();
		this.params = params;
	}

	public Object create(Class<?> clazz, String url, ClassLoader classLoader) {
		Handler handler = new Handler(url, this);
		return Proxy.newProxyInstance(classLoader, new Class[] { clazz }, handler);
	}

	public ParamsData getParams() {
		return params;
	}

}

class Handler implements InvocationHandler {

	private String url;
	private HttpFactory factory;

	public Handler(String url, HttpFactory factory) {
		super();
		this.url = url;
		this.factory =  factory;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//构造请求
		HttpClient httpClient = new HttpClient(); 
		PostMethod post = new PostMethod(this.url);
		//添加请求参数
		HttpClientParams params = new HttpClientParams();
		params.setConnectionManagerTimeout(this.factory.getParams().getTimeout());
		httpClient.setParams(params);
		addHeaders(post); 
		this.factory.getParams().setValues(args);
		//发送请求
		byte[] data = new byte[]{};
		int statusCode = 0;
		try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos)) {
			oos.writeObject(this.factory.getParams());
			data = baos.toByteArray();			
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			post.setRequestEntity(new InputStreamRequestEntity(bis));
			statusCode = httpClient.executeMethod(post);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		//获取响应
		try {
			if(statusCode != HttpStatus.SC_OK){
				throw new Exception("响应码：" + statusCode);
			}
			ObjectInputStream objIn = new ObjectInputStream(post.getResponseBodyAsStream());
			Object result = objIn.readObject();
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			post.releaseConnection();
		}
	}

	private void addHeaders(PostMethod post) {
		Map<String,String> headerParams = this.factory.getParams().getHeaderParams();
		Set<String> keySet = headerParams.keySet();  
        for (String key : keySet) {  
             // 向报文头中添加参数  
           post.addRequestHeader(key, headerParams.get(key));
        }
	}
}