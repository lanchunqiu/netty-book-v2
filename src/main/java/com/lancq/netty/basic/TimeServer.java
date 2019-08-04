package com.lancq.netty.basic;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author lancq
 * @date 2019/8/4
 */
public class TimeServer {
	public static void main(String[] args) throws Exception {
		//
		int port = 8080;
		new TimeServer().bind(port);
	}

	public void bind(int port) throws Exception {
		// ���÷����NIO�߳���
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(boss, worker);
			b.channel(NioServerSocketChannel.class); //ע����ͻ��˵�����NioSocketChannel
			b.option(ChannelOption.SO_BACKLOG, 1024);
			b.childHandler(new ChildChannelHandler());

			// �󶨶˿ڣ�ͬ���ȴ��ɹ�
			ChannelFuture f = b.bind(port).sync();
			System.out.println("����˳ɹ�������port=" + port);

			// �ȴ�����˼����˿ڹر�
			f.channel().closeFuture().sync();
		} finally {
			// �����˳����ͷ��߳���Դ
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}

	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel socketChannel) throws Exception {
			socketChannel.pipeline().addLast(new TimeServerHandler());
		}
	}
}
