package com.lancq.netty.protocol.xml.codec;

import io.netty.handler.codec.http.FullHttpRequest;
import lombok.Data;

/**
 * @author lancq
 */
@Data
public class HttpXmlRequest {
    private FullHttpRequest request;
    private Object body;

    public HttpXmlRequest(FullHttpRequest request, Object body) {
        this.request = request;
        this.body = body;
    }
}
