package com.rpc.server.http;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MethodInvoker;

import com.rpc.factory.ParamsData;
import com.rpc.server.ServerProtocolProcessor;

/**
 * http
 * @author Administrator
 */
public class HttpServerProtocolProcessor implements ServerProtocolProcessor {

	private static Logger logger = LoggerFactory.getLogger(HttpServerProtocolProcessor.class);

	private boolean isDowngrade;// 是否降级 true:是；false：否
	private boolean isUse;// 是否可用
	private Object downgradeProcessor;
	private String clazzName;

	public HttpServerProtocolProcessor( boolean isDowngrade, boolean isUse,
			Object downgradeProcessor, String clazzName) {
		super();
		this.isDowngrade = isDowngrade;
		this.isUse = isUse;
		this.downgradeProcessor = downgradeProcessor;
		this.clazzName = clazzName;
	}

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ObjectInputStream ois = null;
		ObjectOutputStream obo = null;
		try {
			ois = new ObjectInputStream(request.getInputStream());
			ParamsData paramsData = (ParamsData) ois.readObject();
			MethodInvoker methodInvoker = new MethodInvoker();
			methodInvoker.setTargetObject(Class.forName(clazzName).newInstance());
			methodInvoker.setTargetMethod(paramsData.getMethodName());
			methodInvoker.setArguments(paramsData.getValues());
			methodInvoker.prepare();
			Object object = methodInvoker.invoke();
			obo = new ObjectOutputStream(response.getOutputStream());
			obo.writeObject(object);
		} catch (Throwable t) {
			if(ois != null)
				ois.close();
			if(obo != null){
				obo.close();
			}
		}
	}

	@Override
	public String getClazzName() {
		return clazzName;
	}
}