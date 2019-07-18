package com.lancq.netty.pio;

import com.lancq.netty.bio.TimeServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lancq
 */
public class TimeServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port : " + port);
            Socket socket =  null;
            //创建线程池
            TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(50, 100);
            while (true) {
                socket = server.accept();
                singleExecutor.execute(new TimeServerHandler(socket));
            }
        } finally {
            if (server != null) {
                System.out.println("The time server close");
                server.close();
                server = null;
            }
        }
    }
}
