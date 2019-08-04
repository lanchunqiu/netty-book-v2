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
		// 配置服务端NIO线程组
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(boss, worker);
			b.channel(NioServerSocketChannel.class); //注意与客户端的区别，NioSocketChannel
			b.option(ChannelOption.SO_BACKLOG, 1024);
			b.childHandler(new ChildChannelHandler());

			// 绑定端口，同步等待成功
			ChannelFuture f = b.bind(port).sync();
			System.out.println("服务端成功启动，port=" + port);

			// 等待服务端监听端口关闭
			f.channel().closeFuture().sync();
		} finally {
			// 优雅退出，释放线程资源
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
