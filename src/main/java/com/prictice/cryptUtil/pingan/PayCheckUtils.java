package com.prictice.cryptUtil.pingan;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import sun.misc.BASE64Decoder;
import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

public class PayCheckUtils {
	
	/**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
    
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    
    /** *//**
     * 签名算法1
     */
    public static final String SIGNATURE_ALGORITHM1 = "SHA1WithRSA";
	
	
	/**
     * 解压ZIP文件
     * @param zipFile  要解压的ZIP文件路径
     * @param destPath  解压目标路径
     * @throws IOException
     */
	public static void unzip(String zipFile, String destPath) throws Exception {
		ZipFile zip = null;
		ZipEntry entry = null;
		byte[] buffer = new byte[8192];
		int length = -1;
		InputStream input = null;
		BufferedOutputStream bos = null;
		File file = null;
		try {
			zip = new ZipFile(zipFile);
			Enumeration en = zip.entries();
			while (en.hasMoreElements()) {
				entry = (ZipEntry) en.nextElement();
				if (entry.isDirectory()) {
					continue;
				}
				input = zip.getInputStream(entry);
				file = new File(destPath, entry.getName());
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				bos = new BufferedOutputStream(new FileOutputStream(file));
				while (true) {
					length = input.read(buffer);
					if (length == -1) {
						break;
					}
					bos.write(buffer, 0, length);
				}
				bos.close();
				input.close();
			}
			zip.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("解压缩文件抛异常" + ex.getMessage());
		} finally {
			try {
				if (bos != null)
					bos.close();
				if (input != null)
					input.close();
				if (zip != null)
					zip.close();
			} catch (IOException e) {
				throw new Exception("解压缩文件关闭流抛异常" + e.getMessage());
			}
		}
		
	}
	
	
	/**
	 * 3DES算法加解密文件
	 * @param sourceFilePath 用作加密或解密的源文件路径名+文件名
	 * @param targetFilePath 加解密后输出的目标文件路径名+文件名
	 * @param keyGenStr 生成密钥字符串
	 * @param opMode 加解密模式区分(1:加密  2:解密)
	 * @throws Exception
	 */
	public static void encryptOrDecryptBy3DES(String sourceFilePath, String targetFilePath, String keyGenStr, int opMode) throws Exception {
		FileInputStream fis = null;
		CipherInputStream cis = null;
		FileOutputStream fos = null;
		try {
			// 生成密钥
//			if(StringUtils.isEmpty(keyGenStr) || keyGenStr.length() < 24) {
//	            throw new Exception("传入的3DES key长度必须大于24");
//	        }
//			SecretKeySpec keySpec = new SecretKeySpec(keyGenStr.getBytes("UTF-8"), 0, 24, "DESede");
			SecretKeySpec keySpec = new SecretKeySpec(build3DesKey(keyGenStr), "DESede");
			// 创建并初始化密码器
			Cipher cp = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			if (opMode == 1) {
				cp.init(Cipher.ENCRYPT_MODE, keySpec);
			} else if (opMode == 2) {
				cp.init(Cipher.DECRYPT_MODE, keySpec);
			} else {
				throw new Exception("加解密模式区分输入值非法！");
			}
			
			fis = new FileInputStream(sourceFilePath);
			cis = new CipherInputStream(fis, cp);// 创建CipherInputStream对象
			fos = new FileOutputStream(targetFilePath);// 建立文件输出流
			// 如果是加密操作
			if (opMode == 1) {
				int b = 0;
				while ((b = cis.read()) != -1) {
					fos.write((byte) b);
				}
			} else {// 如果是解密操作
				int b = 0;
				while ((b = cis.read()) != -1) {
					fos.write(b);
				}
			}
			cis.close();
			fis.close();
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			//throw new Exception("加解密3DES文件抛异常" + ex.getMessage());
		} finally {
			try {
				if (cis != null)
					cis.close();
				if (fis != null)
					fis.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				//throw new Exception("加解密3DES文件关闭流抛异常" + e.getMessage());
			}
		}
	}
	
	/**
	 * 根据字符串生成密钥字节数组
	 * @param keyStr 生成密钥字符串
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] build3DesKey(String keyStr) {
		byte[] key = new byte[24]; // 声明一个24位的字节数组，默认里面都是0
		byte[] temp = null;
		try {
			temp = keyStr.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			keyStr.getBytes();// 将字符串转成字节数组
		} 

		// 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		if (key.length > temp.length) {
			// 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			// 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}
	
	/**
	 * 计算文件的MD5
	 */
	public static String getMd5ByFile(File file) throws Exception {
		String value = null;
		FileInputStream fis = null;
		MappedByteBuffer byteBuffer=null;
		try {
			fis = new FileInputStream(file);
			byteBuffer = fis.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			byteBuffer.clear();
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("计算文件MD5抛异常" + ex.getMessage());
		} finally {
			try {
				if (null != fis)
					fis.close();
				if(null !=byteBuffer){//释放 MappedByteBuffer的映射内存 否则第二次就会报错
					Cleaner cl = ((DirectBuffer)byteBuffer).cleaner();
			        if (cl != null)
			            cl.clean();
			    	}
			} catch (IOException e) {
				throw new Exception("计算文件MD5关闭流抛异常" + e.getMessage());
			}
		}
		return value;
	}

	/**
	 * 校验数字签名
	 * @param data 已加密数据
	 * @param publicKey 公钥(BASE64编码)
	 * @param sign 数字签名
	 * @return true/false
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		BASE64Decoder base64Decoder = new BASE64Decoder();
		byte [] keyBytes = base64Decoder.decodeBuffer(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(base64Decoder.decodeBuffer(sign));
	}
	// TODO 测试
	public static boolean verify(String content, String sign, String publicKey, String encode) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			byte[] encodedKey = Base64.decodeBase64(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);

			signature.initVerify(pubKey);
			signature.update(content.getBytes(encode));

			boolean bverify = signature.verify(Base64.decodeBase64(sign));
			return bverify;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	/**
	 * 校验数字签名
	 * @param data 已加密数据
	 * @param publicKey 公钥(BASE64编码)
	 * @param sign 数字签名
	 * @param SIGNATURE_ALGORITHM 加密算法
	 * @return true/false
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, String publicKey, String sign,String calculation) throws Exception {
		BASE64Decoder base64Decoder = new BASE64Decoder();
		byte [] keyBytes = base64Decoder.decodeBuffer(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(calculation);
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(base64Decoder.decodeBuffer(sign));
	}
	
	/**
	 * 读取文件内容
	 * @param fileName 文件全路径名
	 * @return String形式文件内容
	 */
	public static String readFile(String fileName) {
		String output = null;
		BufferedReader br = null;
		File file = new File(fileName);
		if (file.exists()) {
			if (file.isFile()) {
				try {
					br = new BufferedReader(new FileReader(file));
					output = IOUtils.toString(br);
				} catch (IOException ioEx) {
					//logger.info("Read File " + fileName + " error!", ioEx);
				}finally{
					try {
						if(br != null){
							br.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				//logger.info("File " + fileName + " is a directory, not a file!");
			}
		} else {
			//logger.info("File " + fileName + " does not exist!");
		}
		return output;
	}
	
	// RSA 加签
	public static String sign(String content, String privateKey) throws Exception{
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			PrivateKey priKey = keyFactory.generatePrivate(priPKCS8);
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(priKey);
			signature.update(content.getBytes());
			byte[] signed = signature.sign();
			return Base64.encodeBase64String(signed);
		} catch (Exception e) {
			throw e;
		}
	}
	
    /**
     * 压缩
     * @param sourceFilePath文件源路径
     * @param zipFilePath目标文件路径
     * @param fileName目标名
     * @return
     */
    public static boolean fileToZip(String sourceFilePath,String zipFilePath,String fileName){  
        boolean flag = false;  
        File sourceFile = new File(sourceFilePath);  
        FileInputStream fis = null;  
        BufferedInputStream bis = null;  
        FileOutputStream fos = null;  
        ZipOutputStream zos = null;  
          
        if(sourceFile.exists() == false){  
            System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");  
        }else{  
            try {  
                File zipFile = new File(zipFilePath + "/" + fileName +".zip");  
                if(zipFile.exists()){  
                    System.out.println(zipFilePath + "目录下存在名字为:" + fileName +".zip" +"打包文件.");  
                }else{  
                    File[] sourceFiles = sourceFile.listFiles();  
                    if(null == sourceFiles || sourceFiles.length<1){  
                        System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");  
                    }else{  
                        fos = new FileOutputStream(zipFile);  
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));  
                        byte[] bufs = new byte[1024*10];  
                        for(int i=0;i<sourceFiles.length;i++){  
                            //创建ZIP实体，并添加进压缩包  
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());  
                            zos.putNextEntry(zipEntry);  
                            //读取待压缩的文件并写进压缩包里  
                            fis = new FileInputStream(sourceFiles[i]);  
                            bis = new BufferedInputStream(fis, 1024*10);  
                            int read = 0;  
                            while((read=bis.read(bufs, 0, 1024*10)) != -1){  
                                zos.write(bufs,0,read);  
                            }
                        }  
                        flag = true;  
                    }  
                }  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } catch (IOException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } finally{  
                //关闭流  
                try {  
                	if(null != bis) bis.close();
                	if(null != fis) fis.close();
                	if(null != zos) zos.close();
                	if(null != fos) fos.close();
                } catch (IOException e) {  
                    e.printStackTrace();  
                    throw new RuntimeException(e);  
                }  
            }  
        }  
        return flag;  
    }  
}
