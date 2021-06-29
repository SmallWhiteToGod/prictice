package com.prictice.cryptUtil.pingan;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;

//import com.paic.qhzx.bank.util.FileTool;

public class TextFileEncrypt {

	// 加密
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//存取基本路径
		String sourPath="D:\\mysql\\pingan";
		// 获取文件
		String fileName="payPlan_YANTAI_20180312";
		
		// 父存放路径
		String destParentPath=sourPath + File.separator + "zipDir";
		// 每日产生zip包路径
		String destPath= destParentPath + File.separator + "20180312";
		
		// 3des密钥
		String keyGenStr = "77f1459a8b66899f1b2aaef0"; 
		// rsa私钥
		String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAK1rbUWPR2APSRWWOpfW6c228DdB9UDBkdupuEUtHi3WyfRpG3qapRRbccrGcxhNgB9/DjKsTwftGpPKNswjOsdxX6c0R3uphgKaNd1sHyXLZEgwYqyIBEtRGxrMiy72YgI0nPn1/gsksYX0HTKVtBk/2yVcfSCEJRNC4Fb4TgStAgMBAAECgYBOpKDxIoCjt0rVV0NLCFMOzx8wb+DgdOfITbca59L/xp/fDqs/dlHWYYHBCy22n66ymdO+dxV/6q5axyECghdtB+paz4FioYa5DNGC0zMPW1CA7V/nTAJOQhLl2iIAyo4qTkMUWekKCLoNKoM0F4uhPPtc2KpUuskPeTHOyyPCAQJBAP/v7Y8YxMJBtlQ29U82CqF0nswzdPE+e9XcAMDpBKIdE+xKJxKC/z5jIt7c7WrKJlkix4l0x8bXsLMqlq5fCdECQQCtdlEpdAT0AOHG+Q1r5kI/62vxKrKg0dmkcdHsJJYiP+1q3rAvGOKJ3OkkaHTmAaWl58K+F1KP+okzSnVo/WgdAkBwF4Xk2UU7iwou4g8YTSPkyK3P422BRwt3g650ztlvouqZ33QzYFon2cVo9DurPLQE7/2STyo8BTVnkuyBnDAxAkBi4OUXCimgpbgN4f5CmXj+UsavLLWycqzrzm3pfFmQoHxHxTUNQ9Qu3hyQQnMQgbPZRKmADm9j317CPBl9haYJAkAV8TFcRuOqxYgJPqdQfj/Ml8id3oCeIRydnGAJFX5RjoJLmwYoUEaE+kspAgeNZ/GxXhd8u37JrQkNrOlgICjM";
		
		try {
			String filePathName = sourPath + File.separator + fileName;
			String des3FileNm = destPath + File.separator + fileName + "_3des";
			
			File destParentFile = new File(destParentPath);
			File destFile = new File(destPath);
			if(!(destParentFile.mkdir()||destFile.mkdir())) {
				throw new Exception("创建文件夹失败！");
			}
			
			// 3des对文件加密
			File file = new File(filePathName);
			if(!file.exists()) {
				throw new Exception("文件不存在！");
			}
			PayCheckUtils.encryptOrDecryptBy3DES(filePathName, des3FileNm, keyGenStr, 1);
			
			// MD5编码
			File des3File = new File(des3FileNm);
			if(!des3File.exists()) {
				throw new Exception("文件不存在！");
			}
			String md5Value = PayCheckUtils.getMd5ByFile(des3File);
			if(md5Value == null) {
				throw new Exception("md5码值生成有误！");
			}
			String fileMd5Name = fileName + "_md5";
//			boolean md5Flag = FileTool.writeFile(md5Value, destPath+ File.separator, fileMd5Name,
//					"UTF-8", false);
//			if (!md5Flag) {
//				throw new Exception("文件md5码生成有误！");
//			}

			String rsaValue = PayCheckUtils.sign(md5Value, privateKey);
			String fileRsaName = fileName + "_rsa";
//			boolean rsaFlag = FileTool.writeFile(rsaValue, destPath+ File.separator, fileRsaName,
//					"UTF-8", false);
//			if (!rsaFlag) {
//				throw new Exception("文件rsa码生成有误！");
//			}

			FileUtils.writeByteArrayToFile(new File(destPath+ File.separator+fileMd5Name),md5Value.getBytes(StandardCharsets.UTF_8));
			FileUtils.writeByteArrayToFile(new File(destPath+ File.separator+fileRsaName),rsaValue.getBytes(StandardCharsets.UTF_8));

			PayCheckUtils.fileToZip(destPath, destParentPath, "payPlan_YANTAI_20180312");
			System.out.println("加密加签文件生成，压缩包目录为：" + destPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
