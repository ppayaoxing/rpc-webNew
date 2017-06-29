package com.admin.mvc;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.admin.common.ServerInfoData;
import com.admin.utils.AdminCacheUtils;

@Controller
public class ServerController {
	
	@RequestMapping(value = "/mvc/server/listServer", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView listServer(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView view = new ModelAndView();
		view.setViewName("index/server");
		Map<String,ServerInfoData> serverList = AdminCacheUtils.getServerList();
		view.addObject("data", serverList);
		return view;
	}
	
	@RequestMapping(value = "/mvc/server/getGroupServer", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getGroupServer(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView view = new ModelAndView();
		view.setViewName("index/groupServer");
		Map<String,Set<String>> urlsMap = AdminCacheUtils.getUrls();
		view.addObject("data", urlsMap);
		return view;
	}

}
