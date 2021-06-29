package com.prictice.util.other;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 ************************************************************
 * @类名 : MD5Util.java
 * @DESCRIPTION :MD5加密类
 * @AUTHOR : fufeixiang
 * @DATE : 2017年6月26日
 ************************************************************
 */
public class MD5Util {
    static final Logger logger = LoggerFactory.getLogger(MD5Util.class);

    /**
     * 计算MD5加密
     *
     * @param s
     * @return
     */
    public static final String getMD5Info(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

        try {
            byte[] btInput = s.getBytes("UTF-8");
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            logger.error("MD5加密失败", e);
            return null;
        }
    }

    /**
     * 根据实体类拼接md5加密串 <功能详细描述>
     *
     * @param obModel 待加密对象
     * @param md5Key 加密密匙
     * @return
     * @throws Throwable
     */
    public static String createMD5SignStr(Object obModel, String md5Key) {

        StringBuilder builder = new StringBuilder();
        String md5SignStr = null;
        Class<? extends Object> objClass = obModel.getClass();
        Field fields[] = objClass.getDeclaredFields();
        List<String> list = new ArrayList<String>();
        for (Field f : fields) {
            if (!f.getName().equals("sign") && !"serialVersionUID".equals(f.getName())) {
                list.add(f.getName());
            }
        }

        Collections.sort(list);

        Field f = null;
        for (String name : list) {
            try {
                f = objClass.getDeclaredField(name);
                f.setAccessible(true);
                builder.append(name).append(f.get(obModel));
            } catch (NoSuchFieldException e) {
                logger.error("拼接MD5加密字符串失败", e);
                return null;
            } catch (SecurityException e) {
                logger.error("拼接MD5加密字符串失败", e);
                return null;
            } catch (IllegalArgumentException | IllegalAccessException e) {
                logger.error("拼接MD5加密字符串失败", e);
                return null;
            }
        }

        String paramStr = builder.toString().concat(md5Key);
        md5SignStr = getMD5Info(paramStr);
        logger.info("计算签名字符串:{},加密后字符串:{}", paramStr, md5SignStr);
        return md5SignStr.toUpperCase();
    }

}
