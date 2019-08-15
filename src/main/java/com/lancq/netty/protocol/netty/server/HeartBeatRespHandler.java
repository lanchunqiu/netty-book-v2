package com.lancq.netty.protocol.netty.server;

import com.lancq.netty.protocol.netty.MessageType;
import com.lancq.netty.protocol.netty.struct.Header;
import com.lancq.netty.protocol.netty.struct.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

/**
 * @author lancq
 */
public class HeartBeatRespHandler extends ChannelHandlerAdapter {
    private static final Logger log = Logger.getLogger(HeartBeatRespHandler.class.getName());
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        // 返回心跳应答消息
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEART_BEAT_REQ.getCode()) {
            log.info("服务端接收到客户端的心跳消息: <--- " + message);
            NettyMessage heartBeat = buildHeatBeat();
            log.info("服务端发送心跳消息到客户端 : ---> " + heartBeat);
            ctx.writeAndFlush(heartBeat);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildHeatBeat() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEART_BEAT_RESP.getCode());
        message.setHeader(header);
        return message;
    }
}
