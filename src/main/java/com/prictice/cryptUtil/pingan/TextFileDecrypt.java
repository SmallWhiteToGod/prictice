package com.prictice.cryptUtil.pingan;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

// 解密
public class TextFileDecrypt {
	public static void main(String[] args) {
		// 3des密钥
		String keyGenStr = "77f1459a8b66899f1b2aaef0"; 
		// RSA公钥
		String rsaPubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCta21Fj0dgD0kVljqX1unNtvA3QfVAwZHbqbhFLR4t1sn0aRt6mqUUW3HKxnMYTYAffw4yrE8H7RqTyjbMIzrHcV+nNEd7qYYCmjXdbB8ly2RIMGKsiARLURsazIsu9mICNJz59f4LJLGF9B0ylbQZP9slXH0ghCUTQuBW+E4ErQIDAQAB";
		//ZIP文件名
		String zipFileName="payPlan_YANTAI_20180312.zip";
		// 待解压缩路径
		String sourcePath="D:\\mysql\\pingan" + File.separator + "zipDir";
		//存取基本路径
		String destParentPath="D:\\mysql\\pingan" + File.separator + "unzipDir";
		String destPath = destParentPath + File.separator + "20180312";
		String name=zipFileName.substring(0, zipFileName.indexOf(".zip"));//YXD_TC_JK_02_201611091136017
		try {
			/**
			 * 解压压缩文件
			 */
			String zipPath=sourcePath+File.separator+zipFileName;//zip文件的路径
			PayCheckUtils.unzip(zipPath, destPath+File.separator+name);
			/*读取md5文件的值*/
			String md5FileNm=destPath+File.separator+name+File.separator+name+"_md5";
			String md5Pama=PayCheckUtils.readFile(md5FileNm);
			if (md5Pama == null) {
				throw new Exception("没有读取到对账文件的md5文件："+md5FileNm);
			}
			// 读取rsa文件
			String rsaFileNm = destPath+File.separator+name+File.separator+name+"_rsa";
			String signPama = PayCheckUtils.readFile(rsaFileNm);
			if (signPama == null) {
				throw new Exception("没有读取到对账文件的rsa文件："+ destPath + rsaFileNm);
			}
			// 验证RSA签名
			byte[] md5ByteArr = md5Pama.getBytes();
			boolean checkSign = PayCheckUtils.verify(md5ByteArr,rsaPubKey,signPama);
			if (!checkSign) {
				throw new Exception("RSA签名不一致，终止对账！");
			}
			//计算3des文件的MD5值
			// String des3FileNm = channel + "_" + fileType + "_" + checkDate +"_3des";
			String des3FileNm = destPath+File.separator+name+File.separator+name+"_3des";
			File des3File = new File(des3FileNm);
			if (!des3File.exists() || des3File.isDirectory()) {
				throw new Exception("没有读取到对账文件的3des文件："+ des3FileNm);
			}
			String md5Value = PayCheckUtils.getMd5ByFile(des3File);
			// // 比较2个MD5值判断3des文件是否传完整一致
			if (!md5Pama.equals(md5Value)) {
				throw new Exception("MD5值不一致，对账文件可能被篡改，终止对账！");
			}
			// // 读取RSA文件中的签名串
			// 解密3des文件
			String retFileNm = "payPlan_YANTAI_20180312_decrypt";//名字自己取 保证唯一性即可
			PayCheckUtils.encryptOrDecryptBy3DES(des3FileNm, destPath+ File.separator + retFileNm, keyGenStr, 2);
			System.out.println("OK!!");

			FileOutputStream fos = null;
			fos = new FileOutputStream(sourcePath+File.separator+"payPlan_YANTAI_20180312_copy.zip");
			FileChannel channel = fos.getChannel();
			channel.write(ByteBuffer.wrap(FileUtils.readFileToByteArray(new File(sourcePath+ File.separator+"payPlan_YANTAI_20180312.zip"))));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
