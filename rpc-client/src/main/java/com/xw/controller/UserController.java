/**
 * 
 */
package com.xw.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import test.rpc.User;
import test.rpc.UserService;

/**
 * 用户动态控制类
 */
@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public String test1(HttpServletRequest request, HttpServletResponse response) throws Exception{
		User user = userService.getUser(10,new User());
		return user.toString();
	}
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/test2", method = RequestMethod.GET)
	@ResponseBody
	public String test2(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return userService.getName();
	}
	
}
