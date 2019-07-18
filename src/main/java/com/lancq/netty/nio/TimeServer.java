package com.lancq.netty.nio;

/**
 * @author lancq
 */
public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        new Thread(new MultiplexerTimeServer(port),"NIO-MultiplexerTimeServer-001").start();
    }
}
