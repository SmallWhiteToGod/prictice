package com.prictice.util.other;

import java.io.Serializable;

public class ResultDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2149785936952647831L;
	/**
	 * 返回结果
	 */
	private String result;
	/**
	 * 扩展码
	 */
	private String returnCode;
	/**
	 * 错误信息
	 */
	private String returnMsg;

	/**
	 *认证中心sid
	 */
	private String sid;
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	/**
	 * 
	 * 结果数据处理
	 */
	private String[] data;

 
	public String[] getData() {
		return data;
	}

	public void setData(String[] data) {
		this.data = data;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	@Override
	public String toString() {
		return "ResultDto [result=" + result + ", extCode=" + returnCode + ", returnMsg=" + returnMsg + "+ ,data="
				+ data + "]";
	}

}
