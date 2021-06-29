package com.prictice.cryptUtil;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;


public class DesUtil {

	private static final String CHARSET = "UTF-8";
	private static final String DES = "DES";
	private static SecretKey secretkey = null;
	
	public static void main(String[] args) throws Exception {
		String s = "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";
		System.out.println(decryptDES(s,"CSIIQZBk"));
	}
	
	private static Key getKey(String key) throws Exception {
		if (secretkey == null) {
			byte[] bb = null;
			bb = key.getBytes(CHARSET);
			secretkey = new SecretKeySpec(bb, DES);
		}
		return secretkey;
	}

	/**
	 * 加密
	 * 
	 * @throws Exception
	 */
	public static String encryptDES(byte[] source,String organkey) throws Exception {
		String s = null;
		byte[] target = null;
		byte[] center = source;
		Key key = getKey(organkey);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		target = cipher.doFinal(center);
		s = Base64.byteArrayToBase64(target);
		return s;
	}

	/**
	 * 加密 Description:
	 * 
	 * @Version1.0 2014-9-30 下午3:46:10
	 * @author hewei（hewei@csii.com.cn）创建
	 * @since
	 * @param encryptString
	 * @return
	 * @throws Exception
	 */
	public static String encryptDES(String encryptString,String organKey) throws Exception {
		return encryptDES(encryptString.getBytes(CHARSET),organKey);
	}

	/**
	 * 解密
	 * 
	 * @throws Exception
	 */
	public static String decryptDES(String source,String organkey) throws Exception {
		String s = null;
		byte[] dissect = null;
		byte[] center = Base64.base64ToByteArray(source);
		Key key = getKey(organkey);
		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(Cipher.DECRYPT_MODE, key);
		dissect = cipher.doFinal(center);
		s = new String(dissect, CHARSET);
		return s;
	}
}
