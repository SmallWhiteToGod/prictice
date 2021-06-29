package com.prictice.cryptUtil.pingan;

import java.nio.charset.Charset;

import org.apache.commons.lang3.RandomStringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

public class TestW {
	public static final String XXX = "请联系壹账通分配";

	@Test
	public void test400010() throws Exception{
		JSONObject data = new JSONObject();
		data.put("apply_no", "x1aw3d12r322212d2312");//小贷贷款标的号
		data.put("bank_apply_no", "MT170226153501");//行方贷款号
		data.put("loan_time", "20170724121212");//放款时间
		data.put("loan_amount", "100");//实际放款金额
		data.put("state", "1");//放款结果
		data.put("message", "");//放款失败原因
		data.put("fee_amount", "10");//一次性手续费
		data.put("trans_req_no", "123");//壹账通企业网银交易流水号
		
		testCient(data, "400010");
	}
	
	public void testCient(JSONObject data, String apiId) throws Exception{
		DesedeCryptSimpleHelper helper = new DesedeCryptSimpleHelper();
		
		String url = "";
		String contentData = "";
		String infoContent = "";
		
		//1-1、针对data进行des加密再base64编码设置。
		infoContent = helper.encrypt(data.toJSONString());
		
		//1-2、补充报文头相关信息，生成报文（json字符串格式）,并base64编码。
		JSONObject body = new JSONObject();
		body.put("api_id", apiId);
		body.put("trans_no", (System.currentTimeMillis())+ RandomStringUtils.random(4, false, true)+RandomStringUtils.random(4, false, true));
		body.put("req_channel_no", XXX);
		body.put("rsp_channel_no", "loan");
		body.put("sign", helper.md5(data.toJSONString(), Charset.forName("UTF-8")));
		body.put("info_content", infoContent);
		
		//1-3、对contentData进行base64编码设置。
		contentData = Base64Util.encode(body.toJSONString());

		//1-4、把1-3中字符串设置在CONTENT_DATA参数值中。
		JSONObject param= new JSONObject();
		param.put("CONTENT_DATA", contentData);
		
		//2、http-post
		String result = restPost(url, param.toJSONString());
		
		//3-1对响应结果进行base64解密
		//Base64Util.decode(result);
		//JSONObject cdata = JSON.parseObject(contentData);
		//System.out.println(cdata.getString("ret_code"));
		//System.out.println(cdata.getString("ret_msg"));

	}
	
	//post-http请求
	public String restPost(String url, String param){
		System.out.println(param);
		return "";
		/*
		 *直连模式
		 	调用服务接口，通过http协议Post请求：
		 	http://172.16.41.233:80/open/appsvr/credoo/outer/openApiProxy?request_sys=XXX（银行缩写）
		 *
		 *
		 *
		 * ESG模式
			1.?????? 获取tokenId，通过http协议Get请求：
			http://172.16.40.181:80/oauth/oauth2/access_token?client_id=P_JJCCB_CREDIT&grant_type=client_credentials&client_secret=x4nj2p5D
			响应报文：
			{"ret":"0","data":{"expires_in":"2772","openid":"客户端名称","access_token":"273752FEEECD45469AEB0ACEB6595FC4"},"msg":""}
			2.???调用服务接口，通过http协议Post请求：
			http://172.16.40.181:80/open/appsvr/credoo/openApi?access_token=上面获取的token（红色，有时效）&request_id=System.currentTimeMillis()&request_sys=XXX（银行缩写）
			响应报文：
			{"ret":"0","data":{},"msg":""}以JSON形式包装在data中。
		*/
	}
	
	@Test
    public void test300010() {
		DesedeCryptSimpleHelper helper = new DesedeCryptSimpleHelper();
		try {
			//1-1从CONTENT_DATA获取报文
			//String requestStr = "eyJpbmZvX2NvbnRlbnQiOiJXUGxOdTBKbjlxa0VGMUp5cVk1YjhFTUtvZk9MbXFJQnFrR2xyYU0v\r\nemdIeG0ycDdVaUtJd3dRWFVuS3BqbHZ3WUowdkhNTjJXYW94XHJcbjdFZWpiRE9jN21ycnUxdE1j\r\nTC8xcHZCTUlvcXh6MlY0OFRZNkx3amdoTDlHeG1wVHAxRXBFaTZuZTdNK2RRc1FMOUJ3N0gxM29J\r\nbG5cclxuaDZUbGFSVHl5V29BM0pWYWNyNTFNTkUyb2pqakszWktHU0V3ZnpvTXNCN1IzOWFlU1Vp\r\neUh5S3BlK1pBTWhIQUhsRlozYndybUpwaVxyXG5CQ0RVNXQwYXY4bnppMkNGNEE9PVxyXG4iLCJz\r\naWduIjoiZDY2YjBhZGZjNmI5MTg3YmM4ZTI2OTZmNjViYjk5YmQiLCJyc3BfY2hhbm5lbF9ubyI6\r\nIiIsImFwaV9pZCI6IiIsInRyYW5zX25vIjoi5ZSv5LiA5oCnIiwicmVxX2NoYW5uZWxfbm8iOiIi\r\nfQ==\r\n";
			String requestStr = "eyJhcGlfaWQiOiIzMDAzMDAiLCJpbmZvX2NvbnRlbnQiOiJRT3lpQlRobHJpQT1cclxuIiwicmVx X2NoYW5uZWxfbm8iOiJsb2FuIiwicmVxX3RpbWUiOiIyMDE5MDgwODIxNTEwMiIsInJldF9jb2Rl IjoiMDYxMTExIiwicmV0X21zZyI6Iuivt+axguS/oeaBr+S4reacieWPguaVsOS/oeaBr+S4uuep uiIsInJzcF9jaGFubmVsX25vIjoiaGxkIiwic2lnbiI6IjM3YTYyNTljYzBjMWRhZTI5OWE3ODY2 NDg5ZGZmMGJkIiwidHJhbnNfbm8iOiIxNTY1MjQ0MDI4NTE4ODI1MTYyMTEifQ==";
			//1-2进行base64解密
			String result = Base64Util.decode(requestStr);
			System.out.println("5:" + result);


			JSONObject contentData = JSON.parseObject(result);
			
			//对报文数据进行解密
			String info = contentData.getString("info_content");
			String infoContent = helper.decrypt(info);
			System.out.println("6:" + infoContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public static void main(String[] args) {
		System.out.println("你好北京");
	}
}