package com.lancq.netty.protocol.netty.struct;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息头
 *
 * @author lancq
 */
@Data
public final class Header {
    /**
     * crc校验码
     */
    private int crcCode = 0xabef0101; //4个字节

    /**
     * 消息长度：包括消息头和消息体
     */
    private int length; //4个字节

    /**
     * 会话ID
     */
    private long sessionID; //8个字节

    /**
     * 消息类型：
     * 0 业务请求消息
     * 1 业务响应消息
     * 2 ONE WAY 消息
     * 3 握手请求消息
     * 4 握手响应消息
     * 5 心跳请求消息
     * 6 心跳应答消息
     */
    private byte type; //1个字节

    /**
     * 消息优先级：0~255
     */
    private byte priority; //1个字节

    /**
     * 附件（可选字段，用于扩展消息头）
     */
    private Map<String, Object> attachment = new HashMap<String, Object>();
}
