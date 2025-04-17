package org.example.imitate.tomcat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private final Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             OutputStream out = socket.getOutputStream()) {
            
            // 读取请求行
            String requestLine = in.readLine();
            if (requestLine == null) return;
            
            logger.info("收到请求: {}", requestLine);
            
            // 解析请求
            String[] requestParts = requestLine.split(" ");
            String method = requestParts[0];
            String path = requestParts[1];
            
            // 构建响应
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html; charset=utf-8\r\n" +
                    "\r\n" +
                    "<html><body><h1>Hello from ImitateTomcat!</h1></body></html>";
            
            out.write(response.getBytes(StandardCharsets.UTF_8));
            out.flush();
            
        } catch (IOException e) {
            logger.error("处理请求时发生错误", e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.error("关闭socket时发生错误", e);
            }
        }
    }
} 