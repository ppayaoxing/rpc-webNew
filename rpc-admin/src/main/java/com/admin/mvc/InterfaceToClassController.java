package com.admin.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.admin.utils.AdminCacheUtils;

@Controller
public class InterfaceToClassController {

	@RequestMapping(value = "/mvc/interClass/getList", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView view = new ModelAndView();
		view.setViewName("index/interfaceClass");
		Map<String,Class<?>> interfaceToClass = AdminCacheUtils.getInterfaceToClass();
		view.addObject("data", interfaceToClass);
		return view;
	}
}
