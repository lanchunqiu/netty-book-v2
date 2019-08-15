package com.lancq.netty.protocol.netty.struct;

import lombok.Data;

/**
 * 消息报文
 *
 * @author lancq
 */
@Data
public final class NettyMessage {
    /**
     * 消息头
     */
    private Header header;

    /**
     * 消息体
     */
    private Object body;
}
