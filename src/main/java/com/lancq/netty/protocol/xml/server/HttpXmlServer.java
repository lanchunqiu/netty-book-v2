package com.lancq.netty.protocol.xml.server;

import com.lancq.netty.protocol.xml.codec.HttpXmlRequestDecoder;
import com.lancq.netty.protocol.xml.codec.HttpXmlResponseEncoder;
import com.lancq.netty.protocol.xml.pojo.Order;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @author lancq
 */
public class HttpXmlServer {
    public static void main(String[] args) throws Exception {
        new HttpXmlServer().run(8080);
    }

    public void run(final int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                            ch.pipeline().addLast("xml-decoder", new HttpXmlRequestDecoder(Order.class, true));
                            ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                            ch.pipeline().addLast("xml-encoder", new HttpXmlResponseEncoder());
                             ch.pipeline().addLast("xmlServerHandler", new HttpXmlServerHandler());
                        }
                    });
            ChannelFuture f = b.bind(port).sync();
            System.out.println("HTTP订购服务器启动，网址是 : " + "http://localhost:" + port);
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
