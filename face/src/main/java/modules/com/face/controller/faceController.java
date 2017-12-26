package com.face.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.face.ResponseData;
import com.face.service.faceService;



/**
 * 登录控制�??
 * @author xuchangcheng
 */
@Controller
@RequestMapping("/face")
public class faceController {
    
	@Autowired
	private faceService face;
	/**
	 * 图片识别
	 * @param request
	 * @return String
	 */
	@RequestMapping(value="pic", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResponseData wh(HttpServletRequest request,MultipartFile pic){
		ResponseData responseData=new ResponseData();
		try{
			Map<String,Object> map = face.pic(request,pic);
			responseData.setStatus(1);
			responseData.setMsg("获取成功");
			responseData.setData(map);
	        }  catch (Exception e) {
			e.printStackTrace();
			responseData.setStatus(2);
			responseData.setMsg("获取失败");
		}
		return  responseData;
	}

}
