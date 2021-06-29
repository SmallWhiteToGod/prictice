package com.prictice.util.ftp;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 苏博
 * @company lhfinance.com
 * @className: Test.java
 * @package com.fintechervision.bmp.service.sdk.sync
 * @description:
 * @date 2019/12/3 13:44
 */
public class Test {
    private static final int timeout = 60 * 1000; //60s
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    /**
     * 获取Session。
     * 失败后间隔1秒发起重试。5次失败后则抛出JschException。
     * @param ip
     * @param port
     * @param user
     * @param pwd
     * @return
     * @throws JSchException 5次建立Session失败后抛出JschException
     */
    private static Session getConnection(String ip, int port, String user, String pwd) throws JSchException {
        Session session = null;
        JSchException jSchException = null;
        // 根据用户名，主机ip，端口获取一个Session对象
        retry:
        for(int i=1; i<5; i++) {
            try {
                JSch jsch = new JSch(); // 创建JSch对象
                session = jsch.getSession(user, ip, port);
                if (pwd != null) {
                    session.setPassword(pwd); // 设置密码
                }
                session.setConfig("StrictHostKeyChecking", "no");
                session.setTimeout(timeout); // 设置timeout时间
                session.connect(); // 通过Session建立链接
                break retry;
            } catch (JSchException e) {
                e.printStackTrace();

                if(session!=null) {
                    session.disconnect();
                    session = null;
                }
                logger.debug("第{}次SFTP建立Session失败，1秒后重试..", i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                jSchException = e;
            }
        }
        if(session==null)
        {
            if(jSchException==null) {
                jSchException = new JSchException("SFTP Session建立异常");
            }
            throw jSchException;
        }
        return session;
    }

    /**
     * 建立连接通道，失败5次后抛出JSchException
     * @param session
     * @return
     * @throws JSchException
     */
    private static Channel connectChannel(Session session) throws JSchException {
        Channel channel = null;
        JSchException jSchException = null;

        for(int i=1; i<5; i++) {
            try {
                channel = session.openChannel("sftp"); // 打开SFTP通道
                channel.connect(); // 建立SFTP通道的连接
            } catch (JSchException e) {
                e.printStackTrace();

                if(channel!=null) {
                    channel.disconnect();
                    channel = null;
                }
                logger.debug("第{}次SFTP建立Channel失败，1秒后重试..", i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                jSchException = e;
            }
        }
        if(channel==null)
        {
            if(jSchException==null) {
                jSchException = new JSchException("SFTP channel建立异常");
            }
            throw jSchException;
        }
        return channel;
    }


    public static byte[] downloadFile(String ip, int port, String user, String pwd, String remotePath){
        logger.debug("downloadFile ip[{}], port[{}], user[{}], pwd[{}], remotePath[{}]"
                , ip, port, user, pwd, remotePath);
        Session session = null;
        Channel channel = null;
        ChannelSftp sftp = null;
        byte[] result = null;
        long st = System.currentTimeMillis();
        try {
            session = getConnection(ip, port, user, pwd);
            channel = connectChannel(session);
            sftp = (ChannelSftp)channel;
            logger.debug("建立连接消耗时间 [{}]",(System.currentTimeMillis()-st));
            // 下载为byte
            st = System.currentTimeMillis();
            InputStream is = sftp.get(remotePath);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buff = new byte[8*1024]; //buff用于存放循环读取的临时数据
            int rc = 0;
            while ((rc = is.read(buff, 0, 100)) > 0) {
                os.write(buff, 0, rc);
            }
            result = os.toByteArray();
            logger.debug("传输内容消耗时间 [{}]",(System.currentTimeMillis()-st));
        } catch (JSchException e) {
            e.printStackTrace();
            throw new RuntimeException("SFTP连接异常");
        } catch (SftpException e) {
            e.printStackTrace();
            logger.debug("[{}]文件下载失败，返回null byte[]");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("[{}]文件读取失败，返回null byte[]");
            return null;
        } finally {
            if(channel!=null) {
                try {
                    channel.disconnect();
                } catch(Exception ignore) {
                    ;
                }
            }
            if(session!=null) {
                try {
                    session.disconnect();
                } catch (Exception ignore) {
                    ;
                }
            }
            if(sftp!=null) {
                try {
                    sftp.disconnect();
                } catch(Exception ignore) {
                    ;
                }
            }
        }
        return result;
    }

    /**
     * ip[192.168.1.204], port[22], user[loan_sftp], pwd[loan123], remotePath[/sftp/batchFile/CTS1201713219441291264.zip]
     * @param args
     * 10.130.1.14
     * ip[10.130.1.14], port[22], user[loan_sftp], pwd[loan123], remotePath[/sftp/batchFile/APS1201860327497109504.zip]
     *
     *  downloadFile ip[192.168.1.204], port[22], user[loan_sftp], pwd[loan123], remotePath[/home/loan_sftp/sftp/batchFile/CTS1201756666458677248.zip]
     */

    public static void main(String[] args) throws Exception{
        byte[] bytes = downloadFile("192.168.1.204",22,"loan_sftp","loan123","/home/loan_sftp/sftp/batchFile/CTS1201756666458677248.zip");
        System.out.println(new String(bytes,"utf-8"));
    }
}
