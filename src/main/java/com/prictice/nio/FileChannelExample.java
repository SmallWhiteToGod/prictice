package com.prictice.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 苏博
 * @version V1.2.0
 * @className: FileChannelExample.java
 * @package com.prictice.demo.nio
 * @description:  Java NIO FileChannel文件通道
 * @date 2019/4/2 17:40
 */
public class FileChannelExample {

    public void example() throws FileNotFoundException, IOException {
        //在使用FileChannel前必须打开通道，打开一个文件通道需要通过输入/输出流或者RandomAccessFile
        RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        //读取文件通道的数据可以通过read方法：
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = inChannel.read(buf);


        String newData = "New String to write to file..." + System.currentTimeMillis();

        buf.clear();
        buf.put(newData.getBytes());
        buf.flip();

        while(buf.hasRemaining()) {
            inChannel.write(buf);
        }
        inChannel.close();
    }
}
