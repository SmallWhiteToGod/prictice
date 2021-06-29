package com.prictice.util.ftp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SeforgeFtpUtils {

	final Logger log = LoggerFactory.getLogger(SeforgeFtpUtils.class);
	/**
	 * 是否覆盖重复文件
	 */
	private boolean isCover = false;

	/**
	 * 
	 * <p>上传</p>
	 * 
	 * @param url 远程url
	 * @param port ftp端口
	 * @param username 用户名
	 * @param password 密码
	 * @param path 上传时存储路径
	 * @param filename 上传文件名
	 * @param input 输入流
	 * @return
	 * @author 钟晓飞 2014-1-15 上午11:38:38
	 */
	public boolean uploadFile(String url, int port, String username, String password, String path, String filename,
			InputStream input) {

		// 初始表示上传失败
		boolean success = false;
		// 创建FTPClient对象
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.connect(url, port);
			// 下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
			ftp.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			// 登录ftp
			ftp.login(username, password);
			// 看返回的值是不是230，如果是，表示登陆成功
			reply = ftp.getReplyCode();
			// 以2开头的返回值就会为真
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				log.info("【FTP文件上传】连接服务器失败");
				return success;
			}
			log.info("【FTP文件上传】登陆服务器成功，文件存储路径为："+ path.concat("/").concat(filename));
			//创建目录
			createDirectory(ftp, path);
			ftp.changeWorkingDirectory(path);// 转移到FTP服务器目录			
			String filename1 = filename;
			if (!isCover) {
				FTPFile[] fs = ftp.listFiles(filename); // 得到目录的相应文件列表
				filename1 = SeforgeFtpUtils.changeName(filename, fs);
			}
			String filename2 = new String(filename1.getBytes("GBK"), "ISO-8859-1");
			String tagertPath = new String(path.getBytes("GBK"), "ISO-8859-1");
			ftp.changeWorkingDirectory(tagertPath);
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			// 如果缺省该句 传输txt正常 但图片和其他格式的文件传输出现乱码
			ftp.storeFile(filename2, input);
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭输入流
			try {
				input.close();
			} catch (IOException e) {
			}
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {

				}
			}
		}
		return success;
	}
	
	
	/**
	 * 
	 * <p>删除</p>
	 * 
	 * @param url 远程url
	 * @param port ftp端口
	 * @param username 用户名
	 * @param password 密码
	 * @param path 路径
	 * @param filename 文件名
	 * @return
	 * @author 钟晓飞 2014-1-15 上午11:41:25
	 */
	public boolean deleteFile(String url, int port, String username, String password, String path, String filename) {
		// 初始表示上传失败
		boolean success = false;
		// 创建FTPClient对象

		FTPClient ftp = new FTPClient();
		try {
			int reply;
			// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.connect(url, port);
			// 下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
			ftp.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			// 登录ftp
			ftp.login(username, password);
			// 看返回的值是不是230，如果是，表示登陆成功
			reply = ftp.getReplyCode();
			// 以2开头的返回值就会为真
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				log.info("【FTP文件删除】连接服务器失败");
				return success;
			}

			log.info("【FTP文件删除】登陆服务器成功，删除文件路径为：" + path.concat("/").concat(filename));
			String filename2 = new String(filename.getBytes("GBK"), "ISO-8859-1");
			String path1 = new String(path.getBytes("GBK"), "ISO-8859-1");
			// 转到指定上传目录
			ftp.changeWorkingDirectory(path1);
			ftp.listFiles(filename2); // 得到目录的相应文件列表
			ftp.deleteFile(filename2);
			ftp.logout();
			success = true;
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			if (ftp.isConnected()) {
				try {

					ftp.disconnect();

				} catch (IOException ioe) {

				}
			}
		}
		return success;

	}

	/**
	 * 
	 * <p>下载</p>
	 * 
	 * @param ip 远程ip
	 * @param port ftp端口
	 * @param username 用户名
	 * @param password 密码
	 * @param remotePath 路径
	 * @param fileName 文件名
	 * @param outputStream 输出流
	 * @param response
	 * @return
	 * @author 钟晓飞 2014-1-15 上午11:43:57
	 */
	public boolean downFile(String ip, int port, String username, String password, String remotePath, String fileName,
			OutputStream outputStream) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(ip, port);
			// 下面三行代码必须要，而且不能改变编码格式
			ftp.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			// 如果采用默认端口，可以使用ftp.connect(url) 的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				log.info("【FTP文件下载】连接服务器失败");
				return success;
			}
			
	        String pathFileName = new String(remotePath.concat("/").concat(fileName).getBytes("GBK"), "ISO-8859-1");
	        
	        log.info("【FTP文件下载】登陆服务器成功，下载文件地址为：" + pathFileName);
	        
			ftp.retrieveFile(pathFileName, outputStream);
			outputStream.flush();
			outputStream.close();

			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("download the File "+ fileName +" fail", e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {

				}
			}
		}
		return success;
	}

	// 判断是否有重名
	public static boolean isDirExist(String fileName, FTPFile[] fs) {
		for (int i = 0; i < fs.length; i++) {
			FTPFile ff = fs[i];
			if (ff.getName().equals(fileName)) {
				return true; // 如果存在返回 正确信号
			}
		}
		return false; // 如果不存在返回错误信号

	}

	// 根据重名判断的结果 生成新的文件的名称
	public static String changeName(String filename, FTPFile[] fs) {
		int n = 0;
		// 创建一个可变的字符串对象 即StringBuffer对象，把filename值付给该对象
		StringBuffer filename1 = new StringBuffer("");
		filename1 = filename1.append(filename);
		System.out.println(filename1);
		while (isDirExist(filename1.toString(), fs)) {
			n++;
			String a = "[" + n + "]";
			System.out.println("字符串a的值是：" + a);
			int b = filename1.lastIndexOf(".");// 最后一出现小数点的位置
			int c = filename1.lastIndexOf("[");// 最后一次"["出现的位置
			if (c < 0) {
				c = b;
			}

			StringBuffer name = new StringBuffer(filename1.substring(0, c));// 文件的名字
			StringBuffer suffix = new StringBuffer(filename1.substring(b + 1));// 后缀的名称
			filename1 = name.append(a).append(".").append(suffix);
		}
		return filename1.toString();
	}

	/**
	 * <p>检索文件</p>
	 * 
	 * @param remotePath "/soft/tmp/"
	 * @param fileName "test21.txt"
	 * @return
	 * @author 杨玲玲 2014-6-11 上午10:17:06
	 */
	public boolean searchFile(String url, int port, String username, String password, String remotePath, String fileName) {

		boolean flag = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.connect(url, port);
			// 下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
			ftp.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			// 登录ftp
			ftp.login(username, password);
			// 看返回的值是不是230，如果是，表示登陆成功
			reply = ftp.getReplyCode();
			// 以2开头的返回值就会为真
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				log.info("【FTP-检索文件】连接服务器失败");
				return false;
			}
			// 转到指定上传目录
			log.info("【FTP】登陆服务器成功=================searchFile================");
            
			FTPFile[] ftpFile = ftp.listFiles(remotePath.concat("/").concat(fileName));	
			flag = ftpFile != null && ftpFile.length > 0 && ftpFile[0] != null && ftpFile[0].isFile();
			log.info("【FTP】检索文件" + remotePath + fileName + ":" + (flag ? "存在" : "不存在"));
			return flag;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("【FTP】检索文件失败IO：" + e.getMessage());
		} finally {
			try {
				ftp.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * <p>移动文件</p>
	 * 
	 * 
	 * @param remotePath "creditloan/LoanSys_UserImg" (相对于根目录)
	 * @param fileName "test21.txt"
	 * @param newPath "delback" (相对于根目录)
	 * @param fileName "test21_bak.txt"
	 * @return
	 */
	public boolean moveFile(String url, int port, String username, String password, 
			String remotePath, String fileName, String newPath, String newFileName) {

		boolean flag = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.connect(url, port);
			// 下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
			ftp.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			// 登录ftp
			ftp.login(username, password);
			// 看返回的值是不是230，如果是，表示登陆成功
			reply = ftp.getReplyCode();
			// 以2开头的返回值就会为真
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				log.info("【FTP-移动文件】连接服务器失败");
				return false;
			}
			
			/*FTPFile[] ftpFile = ftp.listFiles("./"+remotePath+"/"+fileName);
			//如果原文件不存在
		    if(ftpFile == null || ftpFile.length == 0 || ftpFile[0] == null){
		    	log.info("【信贷系统——FTP】移动文件{}不存在，移动失败", (remotePath+"/"+fileName));
		    	return false;
		    }*/
			
			log.info("【FTP】登陆服务器成功=================moveFile================");
			createDirectory(ftp, newPath);
			flag = ftp.rename("./"+remotePath+"/"+fileName, "./"+newPath+"/"+newFileName);
			ftp.logout();		
			log.info("【FTP】移动文件:from={},to={},处理结果={}", (remotePath+"/"+fileName), (newPath+"/"+newFileName), flag);
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("【FTP】移动文件失败：" + e.getMessage());
		} finally {
			try {
				ftp.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * <p>批量移动文件</p>
	 * 
	 * @param pathFiles为一个存放原有文件路径与新存放的文件路径（都相对于FTP的根目录并且加上文件名称）
	 * key为原有文件的路径（如：creditloan/LoanSys_UserImg/test.txt）
	 * value为新文件的存放路径（如：creditloan/moveback/test_back.txt）
	 * @return
	 */
	public boolean batchMoveFile(String url, int port, String username, String password, Map<String,String> pathFiles) {
        if(pathFiles==null || pathFiles.isEmpty()){
        	log.info("【FTP-批量移动文件】移动文件：要移动的文件为空");
        	return false;
        }
		
		boolean flag = true;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.connect(url, port);
			// 下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
			ftp.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			// 登录ftp
			ftp.login(username, password);
			// 看返回的值是不是230，如果是，表示登陆成功
			reply = ftp.getReplyCode();
			// 以2开头的返回值就会为真
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				log.info("【FTP-批量移动文件】连接服务器失败");
				return false;
			}
			log.info("【FTP】登陆服务器成功=================bathMoveFile================");
			Iterator<Entry<String, String>> iter = pathFiles.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
				String oldFilePath = entry.getKey();
				String newFilePath = entry.getValue();
				createDirectory(ftp, newFilePath.substring(0, newFilePath.lastIndexOf("/")));
				boolean subflag = ftp.rename("./"+oldFilePath, "./"+newFilePath);
				log.info("【FTP】移动文件:from={},to={},处理结果={}", oldFilePath, newFilePath, subflag);
				flag = flag && subflag;
			}
			ftp.logout();		
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("【FTP】移动文件失败：" + e.getMessage());
		} finally {
			try {
				ftp.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * <p>创建多级目录</p>
	 * @param FTPClient ftp
	 * @return
	 * @throws IOException 
	 */
    public boolean createDirectory(FTPClient ftp, String filePath) throws IOException{
    	boolean createF = false;
    	if(!StringUtils.isEmpty(filePath)){
    		String path = filePath.replaceAll("\\\\", "/");
    		if(path.startsWith("/")){
    			path = path.substring(1);
    		}
    		if(path.endsWith("/")){
    			path = path.substring(0, path.length());
    		}
    		String[] subPaths = path.split("/");
    		String makePath = subPaths[0];
    		ftp.makeDirectory(makePath);
    		for (int j = 1; j < subPaths.length; j++) {
    			  makePath = makePath.concat("/").concat(subPaths[j]);
    			  createF = ftp.makeDirectory(makePath);
    			  /*System.out.println(makePath + " --- " + createF );*/
    		}
    		subPaths = null;
    	}
    	return createF;
    }
    
    /**
     * <p>删除目录下所有文件</p>
     * @param url
     * @param port
     * @param username
     * @param password
     * @param path
     * @return
     */
    public boolean removeFiles(String url, int port, String username, String password, String path){
    	boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);
			ftp.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			ftp.login(username, password);// 登录
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			FTPFile[] fs = ftp.listFiles(path);
			for (FTPFile f : fs) {
				ftp.deleteFile(path.concat("/").concat(new String(f.getName().getBytes("GBK"), "ISO-8859-1")));
			}
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
			log.error("remove Directory Files"+ path +" fail", e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {

				}
			}
		}
		return success;
    }
    
    
	

	/**
	 * 
	 * @param args
	 * 
	 * @throws FileNotFoundException
	 * 
	 *             测试程序
	 */

	public static void main(String[] args) throws FileNotFoundException {

		/*String path = "";
		File f1 = new File("d:/PublicKey.crt");
		String filename = f1.getName();
		String outfilename = "1205_FSP_CHK_20140107.txt";
		// System.out.println(filename);
		InputStream input = new FileInputStream(f1);
		OutputStream output = new FileOutputStream("d:/1205_FSP_CHK_20140107.txt");
		SeforgeFtpUtils a = new SeforgeFtpUtils();
		// a.setCover(true);
		// a.uploadFile("127.0.0.1", 21, "developer", "developer", path, filename, input);
		a.downFile("127.0.0.1", 21, "developer", "developer", path, outfilename, output);*/
		/*

		 * String path ="D:\\ftpindex\\"; File f2 = new

		 * File("D:\\ftpindex\\old.txt"); String filename2= f2.getName();

		 * System.out.println(filename2); SeforgeFtpUtils a = new

		 * SeforgeFtpUtils(); a.downFile("172.25.5.193", 21, "shi", "123", path,

		 * filename2, "C:\\");

		 */

		// SeforgeFtpUtils a = new SeforgeFtpUtils();
		//
		// a.deleteFile("192.168.0.100", 21, "shiyanming", "123", path, filename);
		
		/*SeforgeFtpUtils sf = new SeforgeFtpUtils();
		sf.uploadFile("192.168.0.199", 21, "ftptst", "ftptst", "creditloan/LoanSys_UserImg","AAA.jpg", new FileInputStream("E:/test/one.jpg"));
		sf.uploadFile("192.168.0.199", 21, "ftptst", "ftptst", "creditloan/LoanSys_UserImg/TPTest/2014-12-29/C201412290000000001",
				"test0000.jpg", new FileInputStream("E:/test/one.jpg"));
		sf.moveFile("192.168.0.199", 21, "ftptst", "ftptst", "creditloan/LoanSys_UserImg", "person.jpg", 
				"creditloan/LoanSys_UserImg/TTB/2014-12-25/C20141226000001", "person_bak.jpg");
		Map<String, String> pathFiles = new HashMap<String, String>();
		pathFiles.put("creditloan/bathImg_Test/one.jpg", "creditloan/bathImg_Test_one/one_back.jpg");
		pathFiles.put("creditloan/bathImg_Test/two.jpg", "bathImg_Test_two/two_back.jpg");
		pathFiles.put("creditloan/bathImg_Test/three.png", "creditloan/LoanSys_UserImg/TTMOVE/2014-12-26/A20141226000001/three_back.png");
		boolean batchRs = sf.batchMoveFile("192.168.0.199", 21, "ftptst", "ftptst", pathFiles);
		System.out.println("批量移动文件：" + batchRs);
		boolean b = sf.removeFiles("192.168.0.199", 21, "ftptst", "ftptst", "abc/test");
		System.out.println(b);
		
		boolean searchB = sf.searchFile("192.168.0.199", 21, "ftptst", "ftptst", "creditloan/LoanSys_UserImg", 
				"FFFBD8B7FBFA464EBE0737A3366F71A6.jpg");
		System.out.println("查找不带有路径的图片：" + searchB);
		boolean searchB1 = sf.searchFile("192.168.0.199", 21, "ftptst", "ftptst", "creditloan/LoanSys_UserImg", 
				"TT/2014-12-26/A20141225000000000001/1AB65F30D2884259879F93C1430FE2C0.jpg");
		System.out.println("查找有路径的图片_1：" + searchB1);
		boolean searchB2 = sf.searchFile("192.168.0.199", 21, "ftptst", "ftptst", "creditloan/LoanSys_UserImg/TT/2014-12-26/A20141225000000000001", 
				"68E38CCF13974B298E2ACEDB0F1A9A37.jpg");
		System.out.println("查找有路径的图片_2：" + searchB2);
		
		
		sf.downFile("192.168.0.199", 21, "ftptst", "ftptst", "creditloan/LoanSys_UserImg", "FFFBD8B7FBFA464EBE0737A3366F71A6.jpg", 
				new FileOutputStream("E:/test/down_nopath_one.jpg"));
		sf.downFile("192.168.0.199", 21, "ftptst", "ftptst", "creditloan/LoanSys_UserImg/TT/2014-12-26/A20141225000000000001", 
				"1AB65F30D2884259879F93C1430FE2C0.jpg",  new FileOutputStream("E:/test/down_path_one.jpg"));
		sf.downFile("192.168.0.199", 21, "ftptst", "ftptst", "creditloan/LoanSys_UserImg", 
				"TT/2014-12-26/A20141225000000000001/68E38CCF13974B298E2ACEDB0F1A9A37.jpg", 
				new FileOutputStream("E:/test/down_path_two.jpg"));*/
	}

	/**
	 * @return the isCover
	 */
	public boolean isCover() {
		return isCover;
	}

	/**
	 * @param isCover the isCover to set
	 */
	public void setCover(boolean isCover) {
		this.isCover = isCover;
	}

}