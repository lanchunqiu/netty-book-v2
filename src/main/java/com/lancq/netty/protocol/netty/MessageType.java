package com.lancq.netty.protocol.netty;

/**
 * 消息类型枚举
 *
 * @author lancq
 */
public enum MessageType {
    BUSINESS_REQ((byte) 0, "业务请求消息"),
    BUSINESS_RESP((byte) 1, "业务响应消息"),

    ONE_WAY((byte) 2, "业务OneWay消息(既是请求又是响应消息)"),

    HAND_SHAKE_REQ((byte) 3, "握手请求消息"),
    HAND_SHAKE_RESP((byte) 4, "握手应答消息"),

    HEART_BEAT_REQ((byte) 5, "心跳请求消息"),
    HEART_BEAT_RESP((byte) 6, "心跳应答消息");

    private byte code;
    private String desc;

    private MessageType(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public byte getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
