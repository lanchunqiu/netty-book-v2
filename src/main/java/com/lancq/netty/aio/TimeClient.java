package com.lancq.netty.aio;

/**
 * @author lancq
 */
public class TimeClient {
    public static void main(String[] args) {
        int port = 8080;
        new Thread(new AsyncTimeClientHandler("localhost", port), "AIO-AAsyncTimeClientHandler-001").start();
    }
}
