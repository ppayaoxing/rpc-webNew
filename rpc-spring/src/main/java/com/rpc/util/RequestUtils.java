package com.rpc.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.rpc.factory.protocol.ValueTypes;

public class RequestUtils {

	public static Object[] getObjects(HttpServletRequest request)
			throws IOException, JsonParseException, JsonMappingException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<ValueTypes> clazz = objectMapper.readValue(request.getHeader("valueTypes"), 
				objectMapper.getTypeFactory().constructParametricType(List.class, ValueTypes.class));
		Object[] value = new Object[clazz.size()];
		for(int i = 0 ; i < clazz.size() ; i++){
			Object obj = objectMapper.readValue(clazz.get(i).getValue(), clazz.get(i).getClazz());
			value[i] = obj;
		}
		return value;
	}
}
