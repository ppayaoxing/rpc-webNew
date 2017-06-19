package com.rpc.factory.protocol.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;

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
		HttpClient httpClient = new HttpClient(); 
		PostMethod post = new PostMethod(this.url);
		this.factory.getParams().setValues(args);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this.factory.getParams());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		byte[] data = baos.toByteArray();
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
//		post.setRequestBody(bis);
		post.setRequestEntity(new InputStreamRequestEntity(bis));
		try {
			httpClient.executeMethod(post);
			ObjectInputStream objIn = new ObjectInputStream(post.getResponseBodyAsStream());
			Object result = objIn.readObject();
			return result;
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		return null;
	}
}