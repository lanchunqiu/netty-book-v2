package com.lancq.netty.basic;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author lancq
 * @date 2019/8/4
 **/
public class TimeClient {
    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        new TimeClient().connect("localhost", port);
    }

    public void connect(String host, int port) throws InterruptedException {
        // 配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.channel(NioSocketChannel.class); //注意与服务端的区别，NioServerSocketChannel
            b.option(ChannelOption.TCP_NODELAY,true);
            b.handler(new ChildChannelHandler());

            // 发起异步连接操作，同步等待连接成功
            ChannelFuture f = b.connect(host, port).sync();
            System.out.println("客户端成功连接，port=" + port);

            // 等待客户端链路关闭
            f.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放NIO线程组
            group.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new TimeClientHandler());
        }
    }
}
