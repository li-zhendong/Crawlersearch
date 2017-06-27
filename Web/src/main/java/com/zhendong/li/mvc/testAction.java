package com.zhendong.li.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhendong.li.dao.testServer;

@Controller
@RequestMapping("/test")
public class testAction {
	@Autowired
    private testServer testl;
	@RequestMapping(value="/get.do",produces = "application/json; charset=utf-8")
	@ResponseBody
	public Object gettest(String air){
		System.out.println(air);
		testl.getSer();
		String sd="sdasd我是测试";
		return sd;
	}
}
