package com.lancq.netty.protocol.xml.codec;

import io.netty.handler.codec.http.FullHttpResponse;
import lombok.Data;

/**
 * @author lancq
 */
@Data
public class HttpXmlResponse {
    private FullHttpResponse httpResponse;
    private Object result;

    public HttpXmlResponse(FullHttpResponse httpResponse, Object result) {
        this.httpResponse = httpResponse;
        this.result = result;
    }
}
