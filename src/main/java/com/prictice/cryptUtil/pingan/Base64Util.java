package com.prictice.cryptUtil.pingan;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
    public static String encode(String data){
        return encode(data, Charset.forName("UTF-8"));
    }

    public static String encode(String data, Charset encoding) {
        byte[] binaryData = data.getBytes(encoding);
        byte[] base64 = encode(binaryData);
        return new String(base64, encoding);
    }

    public static byte[] encode(byte[] data){
        return Base64.encodeBase64(data, true);
    }

    public static String decode(String base64) {
        return decode(base64, Charset.forName("UTF-8"));
    }
    
    /** */
    /**
    * <p>
    * BASE64字符串解码为二进制数据
    * </p>
    *
    * @param base64
    * @return
    * @throws Exception
    */
    public static byte[] decode2Byte(String base64) throws Exception {
        return Base64.decodeBase64(base64.getBytes());
    }
    
    /** */
    /**
    * <p>
    * 二进制数据编码为BASE64字符串
    * </p>
    *
    * @param bytes
    * @return
    * @throws Exception
    */
    public static String decode2String(byte[] bytes) throws Exception {
        return new String(Base64.encodeBase64(bytes));
    }

    public static String decode(String base64, Charset encoding) {
        byte[] binaryData = base64.getBytes(encoding);
        byte[] data = decode(binaryData);
        return new String(data, encoding);
    }

    public static byte[] decode(byte[] base64) {
        return Base64.decodeBase64(base64);
    }
}
