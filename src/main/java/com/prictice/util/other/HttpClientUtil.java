
package com.prictice.util.other;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

//import java.io.UnsupportedEncodingException;
//import org.apache.commons.httpclient.methods.RequestEntity;
//import org.apache.commons.httpclient.methods.StringRequestEntity;

public class HttpClientUtil {
    final static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 该方法支持http post
     * 
     * @param strUrl
     *            请求url
     * @param postData
     *            请求参数信息
     * @param strDecs
     *            交易描述
     * @return
     */
    public  ResultDto excutePost(String strUrl, NameValuePair[] postData, String strDecs) {
        logger.info("HttpClientUtil" + strDecs + "[BEGIN]");
        logger.info("【HttpClientUtil】参数：postData" + postData.toString());
        //
        HttpClient httpClient = new HttpClient();// 创建一个客户端，类似打开一个浏览器
        HttpConnectionManagerParams params = httpClient.getHttpConnectionManager().getParams();
        params.setConnectionTimeout(10000);    //设置连接超时时间
        params.setSoTimeout(10000);            //设置响应超时时间
        // 设定为post方法
        PostMethod postMethod = new PostMethod(strUrl);	
        // 设置请求字符
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");

        /*** 将请求参数转换为json格式*/
        Map<Object,Object> map=new HashMap<Object,Object>();
        for (int i=0;i<postData.length;i++){
            NameValuePair nameValuePair = postData[i];
            if(nameValuePair.getName() == null || nameValuePair.getValue() == null){
                System.out.println(i);
            }
            map.put(nameValuePair.getName(),nameValuePair.getValue());
        }

        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(map);
        String transJson = jsonObject.toString();

        String httpResponse = null;
        InputStreamReader inputStreamReader = null;
        ResultDto ertDto = new ResultDto();
        try {
            logger.info(strDecs + "HttpClientUtil executeMethod");

            //TODO 调换了postMethod.addParameters(postData);的位置，放在setRequestEntity后
            RequestEntity se = new StringRequestEntity(transJson, "application/json", "UTF-8");
            postMethod.setRequestEntity(se);

            // 参数
            postMethod.addParameters(postData);


            httpClient.executeMethod(postMethod);
            // 响应信息码
            int code = postMethod.getStatusCode();
            // 返回的数据码信息不成功
            if (code != HttpStatus.SC_OK) {
                String errMessage = HttpStatus.getStatusText(code);
                logger.info(strDecs + " http statuscode 【" + code + "】 ------errMessage 【" + errMessage + "】");

                ertDto.setResult("N");
                // 错误码为http错误码
                ertDto.setReturnCode(String.valueOf(code));
                // 错误信息为
                ertDto.setReturnMsg("交易连接发生异常，返回http状态码为："+String.valueOf(code));

                return ertDto;
            }
            logger.info(strDecs + "wait");

            //拿到服务器的编码 防止中文乱码
            String charSet =  postMethod.getResponseCharSet();

            inputStreamReader = new InputStreamReader(postMethod.getResponseBodyAsStream(), charSet);

            BufferedReader reader = new BufferedReader(inputStreamReader);  
            StringBuffer stringBuffer = new StringBuffer();  
            String str = "";  
            while((str = reader.readLine())!=null){  
                stringBuffer.append(str);  
            }  
            httpResponse = stringBuffer.toString(); 
            //httpResponse = new String(httpResponse.getBytes("GBK"),"UTF-8");
            logger.info(strDecs + "httpresponse:{}",httpResponse);

        } catch (HttpException e) {
            logger.error("http连接发生异常。", e);
            ertDto.setResult("N");
            ertDto.setReturnMsg("交易连接发生异常。");
            return ertDto;
        } catch (IOException ioe) {
            logger.error("http连接发生IO异常。", ioe);
            ertDto.setResult("N");
            ertDto.setReturnMsg("交易连接发生IO异常。");
            return ertDto;
        } finally {
            IOUtils.closeQuietly(inputStreamReader);
            postMethod.releaseConnection();// 释放链接
        }



        logger.info(strDecs + " END");
        ResultDto resDto = jsonToBean(httpResponse);
        resDto.setResult(httpResponse);
        return resDto;
    }

    /**
     * @param body
     * @return
     */
    private  ResultDto jsonToBean(String body) {
        Gson gson = new Gson();
        ResultDto rtDto = null;
        try {
            rtDto = gson.fromJson(body, ResultDto.class);
            rtDto.setResult("Y");
        } catch (Exception e) {
            rtDto = new ResultDto();
            rtDto.setResult("N");
        }
        return rtDto;
    }

  
    public  String postClient(String urlPath, Map  params, String contentType) {
        logger.info("postClient request params :" + params);
        URL url;
        BufferedReader reader = null;
        OutputStreamWriter writer = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(urlPath);
            try{
             conn = (HttpURLConnection) url.openConnection();
            }catch(Exception e){
                e.printStackTrace();
            }
            conn.setRequestMethod("POST");// 提交模式
            // 是否允许输入输出
            conn.setDoInput(true);
            conn.setDoOutput(true);
            if (StringUtils.isEmpty(contentType)) {
                // 设置请求头里面的数据，以下设置用于解决http请求code415的问题
                conn.setRequestProperty("Content-Type", "application/json");
            } else {
                conn.setRequestProperty("Content-Type", contentType);
            }
            // 链接地址
            conn.connect();
            writer = new OutputStreamWriter(conn.getOutputStream());
            // 发送参数
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(params); 
            writer.write(jsonObject.toString());
            writer.flush();
            StringBuilder responseBuilder = new StringBuilder();
         /*   String charSet =  postMethod.getResponseCharSet();*/
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            logger.info("postClient return:" + responseBuilder.toString());
           
            return responseBuilder.toString();
        } catch (IOException e) {
            logger.error("send request error", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    logger.error("close error", e);
                }
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error("close error", e);
                }
            }

        }

        return null;
    }


    public static void main(String[] args) {
        //  
        //		String strUrl = "http://192.168.0.207:28090/customercenter/card/auth";

        //		 String strUrl ="http://192.168.0.191:9080/customercenter-gateway-http-face-interface/face/faceoneandone";
        //		NameValuePair[] postData = new NameValuePair[16];
        //
        //		//4367480072739637,name=张小波,mobile=18688447009,current=,identifier=511202198205212519,userid=4400009445,
        //
        //		postData[0] = new NameValuePair("systemCode", "CREDITLOAN");
        //		postData[1] = new NameValuePair("operatorId", "4400009445");
        //		postData[2] = new NameValuePair("channelId", "APP");
        //		postData[3] = new NameValuePair("cifseq", "4400007046");
        //		postData[4] = new NameValuePair("priorpub", "0");
        //		postData[5] = new NameValuePair("cardtype", "D");
        //		postData[6] = new NameValuePair("elementType", "3");
        //		postData[7] = new NameValuePair("bankcardno", "4367480072739637");//6227003328080014255
        //		postData[8] = new NameValuePair("cifname", "张小波");
        //		postData[9] = new NameValuePair("certno", "511202198205212519");//140202198604175014
        //		postData[10] = new NameValuePair("leavembl", "18688447009");// 参数不需要
        //		postData[11] = new NameValuePair("bankname", "");// 参数不需要建设银行
        //		postData[12] = new NameValuePair("bankinnercode", "");//01050000
        //		postData[13] = new NameValuePair("signbiz", "CREDITLOAN");
        //		postData[14] = new NameValuePair("signchannel", "APP");
        //		postData[15] = new NameValuePair("mobile", "18688447009");

        //		//风险定价查询
        //		String strUrl = "http://192.168.0.216:8080/CreditWorkEngine/queryEnginePriceResult.do";
        //		NameValuePair[] postData = new NameValuePair[5];
        //		postData[0] = new NameValuePair("systemCode", "1");
        //		postData[1] = new NameValuePair("operatorId","2"); // 操作人
        //		postData[2] = new NameValuePair("channelId", "3");
        //		postData[3] = new NameValuePair("cifseq", "4"); // 客户号
        //		postData[4] = new NameValuePair("orderId", "5");// 订单号
        //		
        //银行卡三要素认证
        //		String strUrl=" http://192.168.0.188:8080/AuthGateway/panAndAccountNameAndIdentifier";
        //		NameValuePair[] postData = new NameValuePair[7];
        //		postData[0] = new NameValuePair("merchant", "1"); // 商户号
        //		postData[1] = new NameValuePair("sid", "2"); // 查询流水号
        //		postData[2] = new NameValuePair("pan", "3"); // 账户
        //		postData[3] = new NameValuePair("name", "4"); // 户名
        //		postData[4] = new NameValuePair("mobile","5"); // 手机号码
        //		postData[5] = new NameValuePair("current", "true"); // 是否实时返回
        //		postData[6] = new NameValuePair("identifier", "6"); // 身份证号
        //		
        //		ResultDto reStr =  new HttpClientUtil().excutePost(strUrl, postData, "测试交易数据");
        //
        //		System.out.println(reStr.getReturnCode());
    }

}
