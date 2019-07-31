package com.lancq.netty.aio;

/**
 * @author lancq
 */
public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        AsyncTimeServerHandler timeServerHandler = new AsyncTimeServerHandler(port);
        new Thread(timeServerHandler, "AIO-AsyncTimeServerHandler-001").start();
    }
}
