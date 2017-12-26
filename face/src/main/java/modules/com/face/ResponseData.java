package com.face;


/**
 * 响应的结果数�?
 * @author yeaker
 *
 */
public class ResponseData {
	
	//执行状�?�结果状态码�?0：成�? 1：失�?
	private Integer status;
	//错误消息
	private String msg;

	//结果数据
	private Object data;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	

}
