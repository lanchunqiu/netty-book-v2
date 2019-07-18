package com.lancq.netty.nio;

/**
 * @author lancq
 */
public class TimeClient {
    public static void main(String[] args) {
        new Thread(new TimeClientHandler("localhost",8080),"TimeClient-001").start();
    }
}
