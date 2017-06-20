package com.rpc.factory.protocol.jsonrpc;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.rpc.factory.ParamsData;
import com.rpc.factory.protocol.ValueTypes;

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
		//构造请求
		HttpClient httpClient = HttpClientBuilder.create()
				.setConnectionTimeToLive(this.factory.getParams().getTimeout()
				, TimeUnit.MILLISECONDS).build();
		HttpPost httpPost = new HttpPost(this.url);
		//设置参数
		BaseParams params = new BaseParams();
		params = params.build(Class.forName(this.factory.getParams().getClazz()), method, args);
		String content = objectMapper.writeValueAsString(params);
		addHeaders(httpPost,args);
		httpPost.setEntity(new StringEntity(content, Charset.forName("UTF-8")));
		//发送
		HttpResponse response = httpClient.execute(httpPost);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			throw new Exception("响应码：" + statusCode);
		}
		String body = EntityUtils.toString(response.getEntity(),"utf-8");
		if (body.contains("error")) {
			throw new Exception(body);
		}
		Result<?> result = objectMapper.readValue(body, objectMapper.getTypeFactory()
				.constructParametricType(Result.class,method.getReturnType()));
		return result.getResult();
	}

	private void addHeaders(HttpPost httpPost,Object[] args) throws IOException, JsonGenerationException, JsonMappingException {
		httpPost.addHeader("Content-type", "application/json; charset=utf-8");
		httpPost.setHeader("Accept", "application/json");
		Map<String,String> headerParams = this.factory.getParams().getHeaderParams();
		  Set<String> keySet = headerParams.keySet();  
          for (String key : keySet) {  
              // 向报文头中添加参数  
        	  httpPost.addHeader(key, headerParams.get(key));  
          }  
		ValueTypes[] values = new ValueTypes[args.length];
		for(int i = 0 ; i < args.length ; i++ ){
			values[i] = new ValueTypes(args[i].getClass(),objectMapper.writeValueAsString(args[i])); 
		}
		httpPost.addHeader("valueTypes", objectMapper.writeValueAsString(values));
	}

}
