package com.prictice.cryptUtil;



import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    
    /**
     * Description: 数据签名
     * @Version1.0 2017-9-21 下午1:36:30 by WangRui (WangRui@csii.com.cn)
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String dataSign(String data , String key) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String hexDataString = key + data + key;
        MessageDigest md = MessageDigest.getInstance("MD5");
        String md5Data = bytesMD52Hex(md.digest((hexDataString.getBytes("UTF-8"))));
        return md5Data;
    }
    
    public static String bytesMD52Hex(byte[] bytes){
        char[] chs  = new char[bytes.length*2];
        for (int i = 0, offset = 0; i < bytes.length; i++) {
            chs[offset++]=HEX[bytes[i]>>4 & 0xf];
            chs[offset++]=HEX[bytes[i] & 0xf];
        }
        return new String(chs);
    }
    private final static char[] HEX="0123456789abcdef".toCharArray();
}
