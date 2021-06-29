package com.prictice.cryptUtil;

/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * 借呗授信申请和结果通知报文加签/验签-SHA256withRSA算法实现demo
 * 
 * 算法:SHA256withRSA 密钥长度:2048
 * 
 * 
 * @author yanqing.qyq
 * @version $Id: RSADemo.java, v 0.1 2016-11-21 15:20 yanqing.qyq Exp $$
 */
public class RSADemo {

	/** 默认编码 */
	private static final String	DEFAULT_ENCODING	= "UTF-8";

	/** 签名算法 */
	private static final String	SIG_ALGORITHM		= "SHA256withRSA";

	/** 银行公钥 */
	private PublicKey			bankPublicKey;

	/** 支付宝私钥 */
	private PrivateKey			alipayPrivateKey;

	public static void main(String[] args) {
		RSADemo rsaDemo = new RSADemo();
		rsaDemo.init();
		/**
		 * 加签 1. 加签内容:request或者response下面所有的内容; 2. 要求:去掉空格和换行 3. 对加签内容进行base64编码 4. 签名 5. 对签名结果进行base64编码
		 */
		// 读入报文内容开始
		String pathname = "D:\\sign.txt";
		String s = null;
		File filename = new File(pathname);
		StringBuilder result = new StringBuilder();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(filename));

			while ((s = br.readLine()) != null) {
				result.append(System.lineSeparator() + s);
			}
			br.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 读入报文内容结束
		String request = getFieldValue("<request>", result.toString());
		// 替换request中的换行与空格
		// request = com.yusys.common.utility.StringUtils.replaceSpecialStr(request);
		System.out.println("获取的报文信息为：" + request);
		String signed = null;
		try {
			// 2. 对加签内容进行base64处理
			signed = rsaDemo.sign(encodeBase64(request.getBytes(DEFAULT_ENCODING)));
			System.out.println("签名串:" + signed);
		} catch (Exception e) {
			System.out.println("签名异常");
		}

		/**
		 * 验签: 1. 验签内容:直接获取request或者response下面的内容; 2. 对签名进行base64解码 3. 对验签内容进行base64编码 4. 验签
		 */
		try {
			if (rsaDemo.verify(signed, encodeBase64(request.getBytes(DEFAULT_ENCODING)))) {
				System.out.println("验签成功");
			} else {
				System.out.println("验签失败");
			}
		} catch (Exception e) {
			System.out.println("验签异常");
		}

	}

	/**
	 * @Title: init
	 * @Description: 初始化方法<br>
	 * @Parmaters:    
	 * @Return: void
	 * @Throws:
	 * @Author:lutong
	 * @CreateDate:2018年12月29日 下午2:50:30
	 * @Version:0.1
	 * @ModifyLog:2018年12月29日 下午2:50:30
	 */
	private void init() {
		try {
			// 私钥 
			String merPriKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDl5eloqFM/QEp5kVXTNwmpTlAMpFXqi8V+B43YkUCGANwlpzQKJKY49fHzPRHRQlMX3XtKkXAKYnx7PSOt0sONyMu7HIkD1FOqfeKCcAxuyzCdpwfl4UVZh+HjgWbGCv+AZS43W4rC/KCWlAjuX1rXbwcA9wUap2Yoen75pj0reThoiCbzt6tw9Fboqhfk0EAoTZGpkZ0E3Qpy/eLW+G0fssqVUkOFR5VfYZhUXDceHNxBjXUxrpOZIJ0Vf0Kk280e0bxnOvumyD1syAQ3hsjhfBWxA0B8pxyLuMM462Di1Z0tPF2ul9yD5BG++o3PbeXLARJIbrQFWNkx6AsgZMSlAgMBAAECggEAA2RkEEZDjDBfqPGLCaXLP7NHHRijp+VOEbD819A02oVSuj+AVhH6XebLHiKti5/l5/k9o3kH5S9U1OCvERaGCiaHUwh1wRe18FMRL4mFtXDME9duF2c+hbaqj5fOM2fgIz3a87gnEP93QyGGDDZd+cXKnaoHakBEEp7UFszsJCMLl6v4LbS+ZKV5SFhYMqWHbpxMzwtrHY3Wk/uZr6MpL7LJHJbGpUj1SpPsIBLSS8UTQOJoeO8oxPj/+Noec6cWy6e00TZFgOgtoXLgdgRP2c3njN3DHxKaeebwpVjO+C0s4I3NGhQEm7zORfsGMLTDsURf3gy5NKkux3pR61vBIQKBgQD45lSpmnNizcJVk1WUHfw6VkU2co1ihGnKQKLbKxqS8K8BQA30H7lRf1ERZZPUR4jsFXPkDLBZ8ZUNYUn3+Ce92fKixmZ5ILPhwTZ0q44/DdTsVfIShb99e711WlAoYFx7ZGYeGavGnRTCuojH/7ducNrDxA8JjZBbe6c8gtOuvQKBgQDsdNDIWuAv6epHzRbzwzG383XYm9Imp2Dy8OsiWMepPqREVhc+LDZ8H0eru3AhjniqByBbDDSTGbUjuxE1BtXTxWaFIf948i0Z2X+VIma0J1/ahmaxUUCxMgW/oVT7XAxPp9R8gMVAlr/zeaTSU0x3WAOXNQ+l3ZqTPtSwhUwgCQKBgQCZlsBdnFJhgOjPl8gyS0KO2ReCnrOCPIVvae1U40dVrzG+ysERiNX5ZWAoJQ9Q6gei7yYBbpcQGZRJmmRjoP0dGTWxnk4zpqt5vpmU6xwu6qEeaXakWWYtz8K8fMuD7tqCxhSBtIOcXugltaecCr8tZnSIYx6IXZPb7/Tl0TFA3QKBgCVSKsH01f26BehfToga/vXxz1/o+DpxoLO3ouVA7gKgiHzgubpucAsXRNCuPPyO6CyuA0Vbn9C39gc1t3mPIqDSP2Q0AYatcsDpu2FmlJ9Kt65eGEdUc8mEA7RDWo5x8N+BteAQ0xFmudVMFd+8lg0gLzDmhxm+6G7lM9zIrUNBAoGBAOBCmTjhCMZ1lGNN2LUhLvpRYcDivB3SE9OOC9wi8En4iHg9w0CtXVfJOKTTjPcajLLMtfIPHsRYDTNnSX/ysJGY39W8YKW3S8mCWgBMprAX2wMq1wKA7o+hB+aiKRtPYujjZ3vS/IBdUAl+Vg1Czjw5JLc0fz8nO9zmlnKVlMGr";
			byte[] privateKeys;
			privateKeys = decodeBase64(merPriKey);
			System.out.println("privateKeys" + privateKeys);
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeys);
			System.out.println("privateKeySpec" + privateKeySpec);
			System.out.println("SIG_ALGORITHM" + StringUtils.substringAfter(SIG_ALGORITHM, "with"));
			KeyFactory mykeyFactory = KeyFactory.getInstance(StringUtils.substringAfter(SIG_ALGORITHM, "with"));

			this.alipayPrivateKey = mykeyFactory.generatePrivate(privateKeySpec);

			// 公钥 
			String bankPubkey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5eXpaKhTP0BKeZFV0zcJqU5QDKRV6ovFfgeN2JFAhgDcJac0CiSmOPXx8z0R0UJTF917SpFwCmJ8ez0jrdLDjcjLuxyJA9RTqn3ignAMbsswnacH5eFFWYfh44Fmxgr/gGUuN1uKwvyglpQI7l9a128HAPcFGqdmKHp++aY9K3k4aIgm87ercPRW6KoX5NBAKE2RqZGdBN0Kcv3i1vhtH7LKlVJDhUeVX2GYVFw3HhzcQY11Ma6TmSCdFX9CpNvNHtG8Zzr7psg9bMgEN4bI4XwVsQNAfKcci7jDOOtg4tWdLTxdrpfcg+QRvvqNz23lywESSG60BVjZMegLIGTEpQIDAQAB";
			
			if (bankPubkey != null || bankPubkey.trim().length() != 0) {
				byte[] pubKeys;
				pubKeys = decodeBase64(bankPubkey);
				X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(pubKeys);
				KeyFactory kf = KeyFactory.getInstance(StringUtils.substringAfter(SIG_ALGORITHM, "with"));
				this.bankPublicKey = kf.generatePublic(pubKeySpec);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 报文签名。
	 * 
	 * @param unsigned
	 *            未加密的报文。
	 * @return 加密之后的报文。
	 */
	public String sign(String unsigned) {

		String signed = null;
		try {

			System.out.println("加签的数据[" + unsigned + "]");

			byte sigData[];
			byte sourceData[] = unsigned.getBytes(DEFAULT_ENCODING);

			// 初始化签名
			Signature sig = Signature.getInstance(SIG_ALGORITHM);
			sig.initSign(alipayPrivateKey);
			sig.update(sourceData);

			// 1. 签名
			sigData = sig.sign();
			System.out.println("Base64前的数据[" + sigData + "]");

			// 2. 对签名结果进行base64编码
			// signed = new String(encodeBase64(sigData));
			signed = encodeBase64(sigData);

		} catch (Exception e) {
			System.out.println("签名异常");
		}
		return signed;
	}

	/**
	 * 报文验签。
	 * 
	 * @param signed
	 *            银行的签名
	 * @param unsigned
	 *            未签名的源报文
	 * @return 验签是否成功
	 */
	public boolean verify(String signed, String unsigned) {

		boolean valid = false;
		try {
			byte sourceData[] = unsigned.getBytes(DEFAULT_ENCODING);

			// base64解密
			byte[] sigData = decodeBase64(signed);
			// 初始化签名
			Signature sig = Signature.getInstance(SIG_ALGORITHM);
			sig.initVerify(bankPublicKey);
			sig.update(sourceData);

			// 验签
			valid = sig.verify(sigData);
		} catch (Exception e) {
			System.out.println("[PointExpressRsaCertifier]签名异常");
		}

		return valid;
	}

	/**
	 * Base64解码
	 */
	public static byte[] decodeBase64(String str) {
		Base64 base64 = new Base64();
		return base64.decode(str.getBytes());
	}

	/**
	 * Base64编码
	 */
	public static String encodeBase64(byte[] b) {
		Base64 base64 = new Base64();
		return new String(base64.encode(b));
	}

	public static String getFieldValue(String field, String msg) {
		int beginIndex = msg.indexOf(field);
		if (beginIndex == -1)
			return "";
		// System.out.println(field.substring(1, field.length()));
		int endIndex = msg.indexOf("</" + field.substring(1, field.length()));
		if (endIndex == -1)
			return "";
		String fieldStr = msg.substring(beginIndex + field.length(), endIndex);
		return fieldStr;
	}

}
