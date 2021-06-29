package com.prictice.util.ftp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <P>FTPclient FTP 上传下载工具类 </P>
 * 
 * @author 杨玲玲 2014-6-9 下午7:22:23
 */
public class FtpClient {

	final Logger logger = LoggerFactory.getLogger(FtpClient.class);

	private String addr;

	private String portStr;

	private String userName;

	private String password;

	private String picPath;

	private String gzFilePath;

	public FtpClient(String addr, String portStr, String userName,
			String password, String picPath, String gzFilePath) {
		super();
		this.addr = addr;
		this.portStr = portStr;
		this.userName = userName;
		this.password = password;
		this.picPath = picPath;
		this.gzFilePath = gzFilePath;
	}

	/**
	 * @return the gzFilePath
	 */
	public String getGzFilePath() {
		return gzFilePath;
	}

	/**
	 * @param gzFilePath the gzFilePath to set
	 */
	public void setGzFilePath(String gzFilePath) {
		this.gzFilePath = gzFilePath;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	/**
	 * <p>connect</p>
	 * 
	 * @param path 上传到ftp服务器哪个路径下
	 * @param addr 地址
	 * @param port 端口号
	 * @param userName 用户名
	 * @param password 密码
	 * @return
	 * @throws Exception
	 * @author 杨玲玲 2014-6-9 下午8:51:39
	 */
	private FTPClient ftpLogin() {
		logger.info("【信贷系统——FTPClient】" + addr + "||" + portStr + "||"
				+ userName + "||" + password);

		FTPClient ftpClient = new FTPClient();
		int reply;
		int port = Integer.parseInt(portStr);
		try {

			if (port > 0) {
				ftpClient.connect(addr, port);
			} else {
				ftpClient.connect(addr);
			}
			ftpClient.setControlEncoding("GBK");
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				logger.error("【信贷系统——FTPClient】登录FTP服务器失败");
				return null;
			}
			logger.info("【信贷系统——FTPClient】连接到FTP服务器：" + addr + " 成功..开始登录");
			ftpClient.login(userName, password);
			// 传输协议
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// ftpClient.changeWorkingDirectory(path);
			logger.info("【信贷系统——FTPClient】登录FTP服务器成功");
		} catch (SocketException e) {
			e.printStackTrace();
			logger.error("【信贷系统——FTPClient】登录FTP服务器失败Socket：" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("【信贷系统——FTPClient】登录FTP服务器失败IO：" + e.getMessage());
		}
		return ftpClient;
	}

	/**
	 * <p>退出FTP</p>
	 * 
	 * @author 杨玲玲 2014-6-11 上午10:14:31
	 */
	private void ftpLogout(FTPClient ftpClient) {
		if (ftpClient != null && ftpClient.isConnected()) {
			try {
				boolean result = ftpClient.logout();
				if (result) {
					logger.info("【信贷系统——FTPClient】成功退出FTP服务器");
				}
			} catch (IOException e) {
				e.printStackTrace();
				logger.warn("【信贷系统——FTPClient】退出FTP服务器异常：" + e.getMessage());
			} finally {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
					logger.warn("【信贷系统——FTPClient】关闭FTP服务器的连接异常");
				}
			}
		}
	}

	//	/**
	//	 * <p>上传文件</p>
	//	 * 
	//	 * @param inputStream
	//	 * @param remotePath "/soft/tmp/test.txt"
	//	 * @return
	//	 * @author 杨玲玲 2014-6-11 上午10:14:54
	//	 */
	//	public boolean uploadFile(InputStream inputStream, String remotePath) {
	//		FTPClient ftpClient = ftpLogin();
	//		if (ftpClient == null) {
	//			return false;
	//		}
	//
	//		boolean flag = false;
	//		int index = remotePath.lastIndexOf("/");
	//		logger.info("【信贷系统——FTPClient】index: " + index);
	//		logger.info("【信贷系统——FTPClient】remotePath.substring(0, index): " + remotePath.substring(0, index + 1));
	//		try {
	//			ftpClient.makeDirectory(remotePath.substring(0, index + 1));
	//			flag = ftpClient.storeFile(remotePath, inputStream);
	//			logger.info("【信贷系统——FTPClient】===============uploadFile==================");
	//			inputStream.close();
	//			logger.info("【信贷系统——FTPClient】file had upload to: " + remotePath);
	//			return flag;
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//			logger.error("【信贷系统——FTPClient】上传文件失败IO：" + e.getMessage());
	//		} finally {
	//			ftpLogout(ftpClient);
	//		}
	//
	//		return flag;
	//	}

	/**
	 * <p>下载文件</p>
	 * 
	 * @param remotePath "/soft/tmp/test.txt"
	 * @param fileName "hello.txt"
	 * @param localPath "E:\\for me\\"
	 * @return
	 * @author 杨玲玲 2014-6-11 上午10:16:19
	 */
	public boolean downloadFile(String remotePath, String fileName,
			String localPath) {
		// ftpClient.retrieveFile(remote, local);
		// ftpClient.storeUniqueFileStream();
		logger.info("downloadFile:{},{},{}", remotePath, fileName, localPath);
		FTPClient ftpClient = ftpLogin();
		if (ftpClient == null) {
			logger.info("【信贷系统——FTPClient】ftpClient == null");
			return false;
		}
		boolean flag = false;

		String localFileName = fileName.replaceAll("\\\\", "/").contains("/") ? fileName
				.substring(fileName.lastIndexOf("/") + 1) : fileName;
		System.out.println(localFileName);
		try (FileOutputStream outputStream = new FileOutputStream(new File(
				localPath, localFileName), false)) {
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			/*int index = remotePath.lastIndexOf("/");
			String path = remotePath.substring(0, index + 1);
			ftpClient.changeWorkingDirectory(path);// 转移到FTP服务器目录		
			logger.info("【信贷系统——FTPClient】path:{}", path);
			flag = ftpClient.retrieveFile(fileName, outputStream);*/
			flag = ftpClient.retrieveFile(remotePath, outputStream);
			logger.info("flag:{}", flag);
			logger.info("【信贷系统——FTPClient】=================downloadFile================");
			logger.info("【信贷系统——FTPClient】file had download from: "
					+ remotePath + " to: " + localPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("【信贷系统——FTPClient】下载文件失败FileNotFound："
					+ e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("【信贷系统——FTPClient】下载文件失败IO：" + e.getMessage());
		} finally {
			ftpLogout(ftpClient);
		}
		return flag;
	}

	/**
	 * <p>检索文件</p>
	 * 
	 * @param remotePath "/soft/tmp/"
	 * @param fileName "test21.txt"
	 * @return
	 * @author 杨玲玲 2014-6-11 上午10:17:06
	 */
	public boolean searchFile(String remotePath, String fileName) {
		FTPClient ftpClient = ftpLogin();
		if (ftpClient == null || remotePath == null || fileName == null) {
			logger.info("【信贷系统——FTPClient】检索文件，请求参数为空，检索文件不存在");
			return false;
		}

		boolean flag = false;
		try {
			logger.info("【信贷系统——FTPClient】=================searchFile================");
			/*ftpClient.changeWorkingDirectory(remotePath);
			FTPFile[] ftpFile = ftpClient.listFiles(fileName);	*/
			FTPFile[] ftpFile = ftpClient.listFiles(remotePath.concat("/")
					.concat(fileName));
			flag = ftpFile != null && ftpFile.length > 0 && ftpFile[0] != null
					&& ftpFile[0].isFile();
			logger.info("【信贷系统——FTPClient】检索文件" + remotePath + "/" + fileName
					+ ":" + (flag ? "存在" : "不存在"));
			return flag;
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("【信贷系统——FTPClient】检索文件失败IO：" + e.getMessage());
		} finally {
			ftpLogout(ftpClient);
		}
		return flag;
	}

	public String getTTTT() {
		return addr;
	}

	public void download(String filePath, String fileName, OutputStream out) {
		if (filePath == null) {
			filePath = picPath;
		}

		FTPClient ftp = ftpLogin();
		try {
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			/*ftp.changeWorkingDirectory(filePath);	
			ftp.retrieveFile(fileName, out);*/
			String pathFile = new String(filePath.concat("/").concat(fileName)
					.getBytes("GBK"), "ISO-8859-1").replaceAll("\\\\", "/");
			boolean b = ftp.retrieveFile(pathFile, out);
			out.flush();
			out.close();
			logger.info("【信贷系统——FTPClient】读取文件路径为：" + pathFile + "，下载结果：" + b);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ftpLogout(ftp);
		}
	}

	public void upload(ByteArrayOutputStream bos, String ftpFilePath,
			String ftpFileName) {
		logger.info("【信贷系统——FTPClient】上传文件路径为："
				+ (picPath + "/" + ftpFilePath + "/" + ftpFileName));
		InputStream input = new ByteArrayInputStream(bos.toByteArray());
		final SeforgeFtpUtils ftpClient = new SeforgeFtpUtils();
		ftpClient.setCover(true);
		ftpClient.uploadFile(addr, new Integer(portStr), userName, password,
				picPath + "/" + ftpFilePath, ftpFileName, input);
	}

	public void uploadImg(InputStream input, String ftpFilePath,
			String ftpFileName) {
		logger.info("【信贷系统——FTPClient】上传文件路径为："
				+ (picPath + "/" + ftpFilePath + "/" + ftpFileName));
		final SeforgeFtpUtils ftpClient = new SeforgeFtpUtils();
		ftpClient.setCover(true);
		ftpClient.uploadFile(addr, new Integer(portStr), userName, password,
				picPath + "/" + ftpFilePath, ftpFileName, input);
	}

	//	/**
	//	 * <p>getGZFile</p>
	//	 * 
	//	 * @return
	//	 * @author 杨玲玲 2014-7-8 下午4:49:18
	//	 */
	//	public BufferedReader getGZFile(String strDate) {
	//		if (strDate == null || strDate.length() == 0) {
	//			Calendar c = Calendar.getInstance();
	//			c.add(Calendar.DATE, -1);
	//			strDate = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
	//		}
	//		FTPClient ftp = ftpLogin();
	//		InputStream input = null;
	//		BufferedReader br = null;
	//		try {
	//			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
	//			if (!Strings.isNullOrEmpty(gzFilePath)) {
	//				ftp.changeWorkingDirectory(gzFilePath);// 转移到FTP服务器目录
	//			}
	//			// input = ftp.retrieveFileStream(gzFilePath + File.separator + "px_" + strDate +
	//			// ".txt.gz");
	//			input = ftp.retrieveFileStream("px_" + strDate + ".txt.gz");
	//			if (input == null) {
	//				logger.info("【信贷系统——FTPClient】px_" + strDate + ".txt.gz" + "不存在，input==null");
	//				return null;
	//			}
	//			logger.info("【信贷系统——FTPClient】input:{}", input);
	//			// ZipUtils.readFromGzip(input, out);
	//			GZIPInputStream gin = new GZIPInputStream(input);
	//			logger.info("【信贷系统——FTPClient】gin:{}", gin);
	//			br = new BufferedReader(new InputStreamReader(gin, "GBK"));
	//			return br;
	//		} catch (Exception e) {
	//			logger.error("【信贷系统——FTPClient】读取gz文件 失败 e:{}", e);
	//			e.printStackTrace();
	//		} finally {
	//			ftpLogout(ftp);
	//		}
	//		logger.info("【信贷系统——FTPClient】读取gz文件end");
	//		return br;
	//	}

	public static void main(String[] args) {
		/*String remotePath = "creditloan/LoanSys_UserImg";
		String fileName = "FFFBD8B7FBFA464EBE0737A3366F71A6.jpg";*/
		String remotePath = "creditloan/LoanSys_UserImg";
		String fileName = "TT/2014-12-26/A20141225000000000001/C42CCEDFCC884BD39249DF390FD8CE7E.jpg";
		String localPath = "target";
		FtpClient client = new FtpClient("192.168.0.199", "21", "ftptst",
				"ftptst", remotePath, remotePath);
		boolean exist = client.searchFile(remotePath, fileName);
		System.out.println("searchFile:" + exist);
		boolean success = client.downloadFile(remotePath + "/" + fileName,
				fileName, localPath);
		System.out.println("download:" + success);

		try {
			client.download(
					null,
					"TT/2014-12-29/A20141229000000000001/4D7ADCA404FC4134971D4D7AE5D8F400.jpg",
					new FileOutputStream("E:/test/download_path_test_2.jpg"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
