package com.admin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.MethodInvoker;

import com.admin.utils.AdminCacheUtils;
import com.admin.web.AdminService;
import com.admin.web.impl.AdminServiceImpl;
import com.rpc.factory.ParamsData;

public class AdminServlet extends HttpServlet {

	/**
	 */
	private static final long serialVersionUID = 1L;

	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		AdminCacheUtils.addInterfaceCalssMap(AdminService.class.getName(), AdminServiceImpl.class);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(req.getInputStream());
			ParamsData paramsData = (ParamsData) ois.readObject();
			Class<?> clazz = AdminCacheUtils.getClazz(paramsData.getClazz());
			MethodInvoker methodInvoker = new MethodInvoker();
			methodInvoker.setTargetObject(clazz.newInstance());
			methodInvoker.setTargetMethod(paramsData.getMethodName());
			methodInvoker.setArguments(paramsData.getValues());
			methodInvoker.prepare();
			Object object = methodInvoker.invoke();
			new ObjectOutputStream(resp.getOutputStream()).writeObject(object);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(ois != null)
				ois.close();
		}
		
	}
	
}
