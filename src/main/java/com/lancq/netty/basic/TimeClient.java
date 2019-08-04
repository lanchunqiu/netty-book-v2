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
        // ���ÿͻ���NIO�߳���
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.channel(NioSocketChannel.class); //ע�������˵�����NioServerSocketChannel
            b.option(ChannelOption.TCP_NODELAY,true);
            b.handler(new ChildChannelHandler());

            // �����첽���Ӳ�����ͬ���ȴ����ӳɹ�
            ChannelFuture f = b.connect(host, port).sync();
            System.out.println("�ͻ��˳ɹ����ӣ�port=" + port);

            // �ȴ��ͻ�����·�ر�
            f.channel().closeFuture().sync();
        } finally {
            // �����˳����ͷ�NIO�߳���
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
