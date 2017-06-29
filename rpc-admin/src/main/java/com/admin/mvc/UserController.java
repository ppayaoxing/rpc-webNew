/**
 * 
 */
package com.admin.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.admin.common.ResponseObject;
import com.admin.common.RestStatus;
import com.admin.utils.StringUtil;

/**
 * 用户动态控制类
 */
@Controller
public class UserController {
	
	/**
	 * 判断用户登录状态
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mvc/checkAuth", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView checkAuth(@Validated String username,String password,HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView view = new ModelAndView();
		if(StringUtil.isEmpty(username)||StringUtil.isEmpty(password)){
			view.setViewName("redirect:/");
			view.addObject("data", "用户名或者密码错误");
			return view;
		}
		request.getSession().setAttribute("username", "jaco");
		request.getSession().setAttribute("userid", 1);
		view.setViewName("index/index");
		return view;
	}
	
	/**
	 * 获取当前用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mvc/currentUser", method = RequestMethod.GET)
	@ResponseBody
	public ResponseObject getCurrentUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Object username=request.getSession().getAttribute("username");
		ResponseObject responseObject=new ResponseObject();
		if(username==null){
			responseObject.setStatus(RestStatus.NOT_CURRENTUSER.code);
			responseObject.setMessage(RestStatus.NOT_CURRENTUSER.message);
			return responseObject;
		}else{
			responseObject.setData(username.toString());
			responseObject.setStatus(RestStatus.SUCCESS.code);
			responseObject.setMessage(RestStatus.SUCCESS.message);
			return responseObject;
		}
	}
	
	/**
	 * 退出
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mvc/unvalidate", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView unValidate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView view = new ModelAndView();
		view.setViewName("redirect:/");
		return view;
	}
	
}
