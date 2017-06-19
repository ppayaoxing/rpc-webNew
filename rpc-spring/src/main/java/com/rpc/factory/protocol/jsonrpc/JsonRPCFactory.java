package com.rpc.factory.protocol.jsonrpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.rpc.factory.ParamsData;

import javafx.beans.property.MapPropertyBase;

public class JsonRPCFactory {

	private ParamsData params;

	public JsonRPCFactory(ParamsData params) {
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
	private ObjectMapper objectMapper = new ObjectMapper();
	private JsonRPCFactory factory;

	public Handler(String url, JsonRPCFactory factory) {
		super();
		this.url = url;
		this.factory =  factory;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(this.url);
		BaseParams params = new BaseParams();
		params = params.build(Class.forName(this.factory.getParams().getClazz()), method, args);
		String content = objectMapper.writeValueAsString(params);
		httpPost.addHeader("Content-type", "application/json; charset=utf-8");
		httpPost.setHeader("Accept", "application/json");
		httpPost.setEntity(new StringEntity(content, Charset.forName("UTF-8")));
		HttpResponse response = httpClient.execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
		}
		String body = EntityUtils.toString(response.getEntity());
		if (body.contains("error")) {
			return null;
		}
		Result<?> result = objectMapper.readValue(body, objectMapper.getTypeFactory()
				.constructParametricType(Result.class,method.getReturnType()));
		return result.getResult();
	}

}