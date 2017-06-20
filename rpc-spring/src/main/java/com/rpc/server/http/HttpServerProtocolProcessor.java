package com.rpc.server.http;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MethodInvoker;

import com.rpc.factory.ParamsData;
import com.rpc.rule.InterceptorRule;
import com.rpc.server.ServerProtocolProcessor;
import com.rpc.util.ObjectCacheUtils;

/**
 * http
 */
public class HttpServerProtocolProcessor implements ServerProtocolProcessor {

	private static Logger logger = LoggerFactory.getLogger(HttpServerProtocolProcessor.class);

	private boolean isDowngrade;// 是否降级 true:是；false：否
	private boolean isUse;// 是否可用
	private Object downgradeProcessor;
	private String clazzName;

	public HttpServerProtocolProcessor(boolean isDowngrade, boolean isUse, Object downgradeProcessor,
			String clazzName) {
		super();
		this.isDowngrade = isDowngrade;
		this.isUse = isUse;
		this.downgradeProcessor = downgradeProcessor;
		this.clazzName = clazzName;
	}

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			if (!isUse) {
				response.setStatus(410);
				return;
			}
			if (!perHandler(request, response)) {
				response.setStatus(410);
				return;
			}
			if (isDowngrade && ObjectCacheUtils.containClusterKey(this.clazzName, request.getHeader("clusterKey"))) {
				if (downgradeProcessor != null) {
					deal(request, response.getOutputStream());
					return;
				}
				logger.warn("downgradeProcessor 引用对象不存在");
				return;
			}
			invoker(request, response);
		} finally {
			afterHandler(request, response);
		}
	}

	private void invoker(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
			if (ois != null)
				ois.close();
			if (obo != null) {
				obo.close();
			}
		}
	}
	
	/**
	 * 后置拦截
	 * @param request
	 * @param response
	 */
	private void afterHandler(HttpServletRequest request, HttpServletResponse response) {
		List<InterceptorRule> rules = ObjectCacheUtils.getRules();
		for (InterceptorRule rule : rules) {
			try {
				rule.afterCompletion(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 前置拦截
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean perHandler(HttpServletRequest request, HttpServletResponse response) {
		List<InterceptorRule> rules = ObjectCacheUtils.getRules();
		for (InterceptorRule rule : rules) {
			try {
				if (!rule.preHandle(request, response))
					return false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 降级处理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private void deal(HttpServletRequest request, OutputStream output) throws Exception {
		MethodInvoker methodInvoker = new MethodInvoker();
		methodInvoker.setTargetObject(downgradeProcessor);
		methodInvoker.setTargetMethod(request.getHeader("methodName"));
		methodInvoker.prepare();
		Object object = methodInvoker.invoke();
		new ObjectOutputStream(output).writeObject(object);
	}

	@Override
	public String getClazzName() {
		return clazzName;
	}
}