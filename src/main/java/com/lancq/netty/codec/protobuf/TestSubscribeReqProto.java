package com.lancq.netty.codec.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lancq.netty.codec.protobuf.SubscribeReqProto.SubscribeReq;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author lancq
 * @date 2019/8/11
 **/
public class TestSubscribeReqProto {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReq req = createSubscribeReq();
        System.out.println("Before encode : " + req);

        SubscribeReq req2 = decode(encode(req));
        System.out.println("after encode : " + req2);

        System.out.println("assert equal : " + req2.equals(req));
    }

    private static byte[] encode(SubscribeReq req){
        return req.toByteArray();
    }

    private static SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReq.parseFrom(body);
    }
    private static SubscribeReqProto.SubscribeReq createSubscribeReq(){
        SubscribeReq.Builder builder = SubscribeReq.newBuilder();
        builder.setSubReqId(1);
        builder.setUserName("lancq");
        builder.setProductName("Netty Book");
        builder.setAddress("Beijing LiuLiChang");
        return builder.build();
    }
}
