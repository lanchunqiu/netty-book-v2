package com.lancq.netty.protocol.netty.server;

import com.lancq.netty.protocol.netty.MessageType;
import com.lancq.netty.protocol.netty.struct.Header;
import com.lancq.netty.protocol.netty.struct.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * @author lancq
 */
public class LoginAuthRespHandler extends ChannelHandlerAdapter {

    private static final Logger log = Logger.getLogger(LoginAuthRespHandler.class.getName());

    private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<String, Boolean>();
    private List<String> whiteList = Arrays.asList("127.0.0.1");


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        // 如果是握手请求消息，处理，其它消息透传
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.HAND_SHAKE_REQ.getCode()) {
            String nodeIndex = ctx.channel().remoteAddress().toString();
            NettyMessage loginResp = null;
            // 重复登录，拒绝
            if (nodeCheck.containsKey(nodeIndex)) {
                loginResp = buildResponse((byte) -1);
            } else {
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                String ip = address.getAddress().getHostAddress();
                log.info(ip);
                boolean isOk = false;
                if (whiteList.contains(ip)) {
                    isOk = true;
                }
                loginResp = isOk ? buildResponse((byte) 0) : buildResponse((byte) -1);
                if (isOk) {
                    nodeCheck.put(nodeIndex, true);
                }
            }
            log.info("The login response is :" + loginResp + " body [" + loginResp.getBody() + "]");
            ctx.writeAndFlush(loginResp);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        nodeCheck.remove(ctx.channel().remoteAddress().toString());// 删除缓存
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }

    private NettyMessage buildResponse(byte result) {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HAND_SHAKE_RESP.getCode());
        message.setHeader(header);
        message.setBody(result);
        return message;
    }
}
