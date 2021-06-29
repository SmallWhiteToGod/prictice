package com.prictice.nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author 苏博
 * @version V1.2.0
 * @company lhfinance.com
 * @className: ChannelTransfers.java
 * @package com.prictice.demo.nio
 * @description: 在Java NIO中如果一个channel是FileChannel类型的，那么可以直接把数据传输到另一个channel。
 * ************* 逐个特性得益于FileChannel包含的 transferTo 和 transferFrom 两个方法。
 * @date 2019/4/2 15:53
 */
public class ChannelTransfers {

    public FileChannel channelTransfer() throws Exception{
        RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

        toChannel.transferFrom(fromChannel, position, count);
        return toChannel;
    }


    public static void main(String[] args) throws Exception{

        ChannelTransfers transfers = new ChannelTransfers();
        FileChannel fileChannel = transfers.channelTransfer();
    }


}
