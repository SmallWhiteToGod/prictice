package com.prictice.cryptUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * @author 苏博
 * @version V1.2.0
 * @company lhfinance.com
 * @className: Base64.java
 * @package com.prictice.util.encrypt
 * @description:  做Md5摘要工具类
 * @date 2019/5/7 11:35
 */
public class ClassSummaryUtil {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static String getSummaryCode(String message) {

        String md5Str = null;
        try {
            md5Str = bytes2Hex(MessageDigest.getInstance("MD5").digest(message.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Str;
    }

    // 2进制转16进制
    public static String bytes2Hex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        int temp;
        try {
            for (int i = 0; i < bytes.length; i++) {
                temp = bytes[i];
                if(temp < 0) {
                    temp += 256;
                }
                if (temp < 16) {
                    result.append("0");
                }
                result.append(Integer.toHexString(temp));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
