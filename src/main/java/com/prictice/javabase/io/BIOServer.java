package com.prictice.javabase.io;

        import java.io.IOException;
        import java.io.InputStream;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.util.concurrent.ExecutorService;
        import java.util.concurrent.Executors;

/**
 * @author 苏博
 * @company lhfinance.com
 * @className: BIOServer.java
 * @package com.prictice.javabase.io.io
 * @description: bio模拟 服务端
 * @date 2019/9/17 15:17
 */
public class BIOServer {

    public static void main(String[] args) throws IOException {
        // TODO 服务端处理客户端连接请求
        ServerSocket serverSocket = new ServerSocket(3333);
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        // 接收到客户端连接请求之后为每个客户端创建一个新的线程进行链路处理
        while (true) {
            try {
                // 阻塞方法获取新的连接
                Socket socket = serverSocket.accept();

                // 每一个新的连接都创建一个线程，负责读取数据
                threadPool.execute(() -> {
                    try {
                        int len;
                        byte[] data = new byte[1024];
                        InputStream inputStream = socket.getInputStream();
                        // 按字节流方式读取数据
                        while ((len = inputStream.read(data)) != -1) {
                            System.out.println(new String(data, 0, len));
                        }
                    } catch (IOException e) {
                    }
                });

            } catch (IOException e) {
            }

        }

    }

}
