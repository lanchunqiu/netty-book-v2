package com.lancq.netty.codec.pojo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author lancq
 */
@Data
public class SubscribeReq implements Serializable {
    /**
     * 默认的序列号ID
     */
    private static final long serialVersionUID = 1L;

    private int subReqID;

    private String userName;

    private String productName;

    private String phoneNumber;

    private String address;
}
