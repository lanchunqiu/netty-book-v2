package com.lancq.netty.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author lancq
 *
 */
public class AsyncTimeServerHandler implements Runnable {
    private int port;
    private CountDownLatch latch;
    private AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AsyncTimeServerHandler(int port) {
        this.port = port;
        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("The time server is start in port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        latch = new CountDownLatch(1);
        doAccept();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doAccept() {
        asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler());
    }

    public AsynchronousServerSocketChannel getAsynchronousServerSocketChannel() {
        return this.asynchronousServerSocketChannel;
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}