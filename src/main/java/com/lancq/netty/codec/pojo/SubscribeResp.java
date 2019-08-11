package com.lancq.netty.codec.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lancq
 */
@Data
public class SubscribeResp implements Serializable {
    /**
     * 默认序列ID
     */
    private static final long serialVersionUID = 1L;

    private int subReqID;

    private int respCode;

    private String desc;
}
