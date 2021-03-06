package com.prictice.util.other;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * Http工具类
 * 1、发送GET请求；
 * 2、发送POST请求。
 * 
 */

public class HttpUtil {
	final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	/**
	 * 
	 * <p>
	 * 发送GET请求 
	 * @param url GET请求地址
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 */

	public static ResultDto doGet(String url) {
		return HttpUtil.doGet(url, null, null, 0);
	}

	/**
	 * 
	 * <p>
	 * 发送GET请求
	 * @param url   GET请求地址
	 * @param headerMap   GET请求头参数容器
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 */

	public static ResultDto doGet(String url, Map headerMap) {
		return HttpUtil.doGet(url, headerMap, null, 0);
	}

	/**
	 * 
	 * <p>
	 * 发送GET请求
	 * @param url  GET请求地址
	 * @param proxyUrl  代理服务器地址 
	 * @param proxyPort  代理服务器端口号
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 */

	public static ResultDto doGet(String url, String proxyUrl, int proxyPort) {
		return HttpUtil.doGet(url, null, proxyUrl, proxyPort);
	}

	/**
	 * 
	 * <p>
	 * 发送GET请求
	 * @param url  GET请求地址
	 * @param headerMap  GET请求头参数容器
	 * @param proxyUrl  代理服务器地址
	 * @param proxyPort  代理服务器端口号
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 */

	public static ResultDto doGet(String url, Map headerMap, String proxyUrl, int proxyPort) {
		String httpRespContent = null;
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		// 头部请求信息
		if (headerMap != null) {
			Iterator iterator = headerMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry entry = (Entry) iterator.next();
				getMethod.addRequestHeader(entry.getKey().toString(), entry.getValue().toString());
			}
		}

		if (StringUtils.isNotBlank(proxyUrl)) {
			httpClient.getHostConfiguration().setProxy(proxyUrl, proxyPort);
		}

		//设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里你也可以设置成自定义的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 10000);
		
		// postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER ,
		// new DefaultHttpMethodRetryHandler());

		InputStreamReader inputStreamReader = null;
		ResultDto ertDto = new ResultDto();
		try {
			
			httpClient.executeMethod(getMethod);
			int httpCode = getMethod.getStatusCode();
			ertDto.setReturnCode(String.valueOf(httpCode));
			
			if (httpCode == HttpStatus.SC_OK) {
				//拿到服务器的编码
				String charSet =  getMethod.getResponseCharSet();

				inputStreamReader = new InputStreamReader(getMethod.getResponseBodyAsStream(), charSet);
				
				BufferedReader reader = new BufferedReader(inputStreamReader);  
				StringBuffer stringBuffer = new StringBuffer();  
				String str = "";  
				while((str = reader.readLine())!=null){  
				    stringBuffer.append(str);  
				}  
				httpRespContent = stringBuffer.toString(); 
				ertDto.setResult(httpRespContent);
			} else {
				String errMessage = HttpStatus.getStatusText(httpCode);
				logger.error("http statuscode 【" + httpCode + "】 ------errMessage 【" + errMessage + "】");
				ertDto.setResult("N");
				// 错误信息为
				ertDto.setReturnMsg("交易连接发生异常，返回http状态码为："+String.valueOf(httpCode));	
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			logger.error("交易连接发生系统异常",ex);
			ertDto.setResult("N");
			ertDto.setReturnMsg("交易连接发生系统异常。");
			return ertDto;
		} finally {
			IOUtils.closeQuietly(inputStreamReader);
			getMethod.releaseConnection();
		}

		return ertDto;

	}

	/**
	 * 
	 * <p>
	 * 发送POST请求
	 * @param url   POST请求地址
	 * @param parameterMap  POST请求参数容器
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 */

	public static byte[] doPost(String url, Map parameterMap) {
		return HttpUtil.doPost(url, null, parameterMap, null, null, 0);
	}

	/**
	 * 
	 * 发送POST请求
	 * @param url   POST请求地址
	 * @param parameterMap  POST请求参数容器
	 * @param paramCharset   参数字符集名称 
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 */

	public static byte[] doPost(String url, Map parameterMap, String paramCharset) {
		return HttpUtil.doPost(url, null, parameterMap, paramCharset, null, 0);
	}

	/**
	 * 
	 * 发送POST请求
	 * 
	 * @param url   POST请求地址
	 * @param headerMap  POST请求头参数容器     
	 * @param parameterMap  POST请求参数容器 
	 * @param paramCharset  参数字符集名称
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 */

	public static byte[] doPost(String url, Map headerMap, Map parameterMap, String paramCharset) {
		return HttpUtil.doPost(url, headerMap, parameterMap, paramCharset, null, 0);
	}

	/**
	 * 
	 * 
	 * 发送POST请求
	 * @param url  POST请求地址
	 * @param parameterMap   POST请求参数容器
	 * @param paramCharset  参数字符集名称
	 * @param proxyUrl  代理服务器地址
	 * @param proxyPort  代理服务器端口号
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 */

	public static byte[] doPost(String url, Map parameterMap, String paramCharset, String proxyUrl, int proxyPort) {
		return HttpUtil.doPost(url, null, parameterMap, paramCharset, proxyUrl, proxyPort);
	}

	/**
	 * 
	 * 发送POST请求
	 * 
	 * @param url  POST请求地址 
	 * @param headerMap  POST请求头参数容器
	 * @param parameterMap   POST请求参数容器
	 * @param paramCharset  参数字符集名称
	 * @param proxyUrl  代理服务器地址
	 * @param proxyPort  代理服务器端口号
	 * 
	 * @return 与当前请求对应的响应内容字节数组
	 * 
	 */

	public static byte[] doPost(String url, Map headerMap, Map parameterMap, String paramCharset, String proxyUrl,
			int proxyPort) {

		byte[] content = null;
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		if (StringUtils.isNotBlank(paramCharset)) {
			postMethod.getParams().setContentCharset(paramCharset);
			postMethod.getParams().setHttpElementCharset(paramCharset);
		}

		// 头部请求信息
		if (headerMap != null) {
			Iterator iterator = headerMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry entry = (Entry) iterator.next();
				postMethod.addRequestHeader(entry.getKey().toString(), entry.getValue().toString());
			}
		}

		Iterator iterator = parameterMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			postMethod.addParameter(key, (String) parameterMap.get(key));
		}

		if (StringUtils.isNotBlank(proxyUrl)) {
			httpClient.getHostConfiguration().setProxy(proxyUrl, proxyPort);
		}

		// 设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里你也可以设置成自定义的恢复策略

		postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 10000);

		// postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER ,
		// new DefaultHttpMethodRetryHandler());

		InputStream inputStream = null;
		try {
			if (httpClient.executeMethod(postMethod) == HttpStatus.SC_OK) {
				// 读取内容
				inputStream = postMethod.getResponseBodyAsStream();
				content = IOUtils.toByteArray(inputStream);
			} else {
				System.err.println("Method failed: " + postMethod.getStatusLine());
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(inputStream);
			postMethod.releaseConnection();
		}

		return content;

	}
	
	
	
	
	

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("wd", "lakala");
		ResultDto dto = doGet("http://www.baidu.com", map);
		System.out.println("-------------------" + dto);

	}

}
