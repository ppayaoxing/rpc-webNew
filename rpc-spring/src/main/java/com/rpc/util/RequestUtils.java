package com.rpc.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.HttpStatus;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.rpc.factory.ParamsData;
import com.rpc.factory.protocol.ValueTypes;

public class RequestUtils {

	public static Object[] getObjects(HttpServletRequest request)
			throws IOException, JsonParseException, JsonMappingException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<ValueTypes> clazz = objectMapper.readValue(request.getHeader("valueTypes"),
				objectMapper.getTypeFactory().constructParametricType(List.class, ValueTypes.class));
		Object[] value = new Object[clazz.size()];
		for (int i = 0; i < clazz.size(); i++) {
			Object obj = objectMapper.readValue(clazz.get(i).getValue(), clazz.get(i).getClazz());
			value[i] = obj;
		}
		return value;
	}

	public static Object send(String url, ParamsData paramsData) throws Exception {
		// 构造请求
		HttpClient httpClient = new HttpClient();
		PostMethod post = new PostMethod(url);
		// 添加请求参数
		HttpClientParams params = new HttpClientParams();
		params.setConnectionManagerTimeout(paramsData.getTimeout());
		httpClient.setParams(params);
		addHeaders(post, paramsData);
		// 发送请求
		byte[] data = new byte[] {};
		int statusCode = 0;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos)) {
			oos.writeObject(paramsData);
			data = baos.toByteArray();
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			post.setRequestEntity(new InputStreamRequestEntity(bis));
			statusCode = httpClient.executeMethod(post);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		// 获取响应
		try {
			if (statusCode != HttpStatus.SC_OK) {
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

	private static void addHeaders(PostMethod post, ParamsData paramsData) {
		Map<String, String> headerParams = paramsData.getHeaderParams();
		Set<String> keySet = headerParams.keySet();
		for (String key : keySet) {
			// 向报文头中添加参数
			post.addRequestHeader(key, headerParams.get(key));
		}
	}
}
