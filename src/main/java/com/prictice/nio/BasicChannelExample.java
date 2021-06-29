package com.prictice.nio;

/**
 * @author 苏博
 * @version V1.2.0
 * @company lhfinance.com
 * @className: BasicChannelExample.java
 * @package com.prictice.demo.nio
 * @description:  利用FileChannel读取数据到Buffer的例子
 *  nio的组件 *********  Channels
 *  nio的组件 *********  Buffers
 *  nio的组件 *********  Selectors
 * @date 2019/4/2 14:29
 */

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * Java NIO中最重要的集中Channel的实现：
 * FileChannel用于文件的数据读写。
 * DatagramChannel用于UDP的数据读写。
 * SocketChannel用于TCP的数据读写。
 * ServerSocketChannel允许我们监听TCP链接请求，每个请求会创建会一个SocketChannel.
 */
public class BasicChannelExample {

    public void  readFileChannel() throws IOException{
        try {

            RandomAccessFile aFile = new RandomAccessFile("nio-data.txt", "rw");
            FileChannel inChannel = aFile.getChannel();

            //设置容量
            ByteBuffer buf = ByteBuffer.allocate(48);

            int bytesRead = inChannel.read(buf);
            while (bytesRead != -1) {

                System.out.println("Read " + bytesRead);
                buf.flip();

                while (buf.hasRemaining()) {
                    System.out.print((char) buf.get());
                }

                buf.clear();
                bytesRead = inChannel.read(buf);
            }
            aFile.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }


    public static void main(String[] args) throws IOException{
        BasicChannelExample bce = new BasicChannelExample();
        bce.readFileChannel();
    }

}
