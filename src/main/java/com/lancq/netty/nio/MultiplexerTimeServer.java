package com.lancq.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * 多路复用器
 *
 * @author lancq
 */
public class MultiplexerTimeServer implements Runnable {

    private Selector selector;

    private ServerSocketChannel serverChannel;

    private volatile boolean stop;

    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(port), 1024);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectedKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        this.handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        // 多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            //处理新接入的请求消息
            if (key.isAcceptable()) {
                //accept the new connection
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                //add the new connection to the selector
                sc.register(selector, SelectionKey.OP_READ);
            } else if (key.isReadable()) {
                //read the data
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(byteBuffer);
                if (readBytes > 0) {
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order : " + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                    doWrite(sc, currentTime);
                } else if (readBytes < 0) {
                    //对端链路关闭
                    key.channel();
                    sc.close();
                } else {
                    //读到0字节
                    //to do nothing
                }
            }

        }
    }

    private void doWrite(SocketChannel sc, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            sc.write(writeBuffer);
        }
    }
}
