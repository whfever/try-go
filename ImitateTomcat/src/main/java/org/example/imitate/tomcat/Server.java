package org.example.imitate.tomcat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private final int port;
    private final ExecutorService executorService;
    private boolean running = false;

    public Server(int port) {
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public void start() {
        running = true;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("服务器启动，监听端口: {}", port);
            
            while (running) {
                Socket socket = serverSocket.accept();
                executorService.execute(new RequestHandler(socket));
            }
        } catch (IOException e) {
            logger.error("服务器异常", e);
        }
    }

    public void stop() {
        running = false;
        executorService.shutdown();
        logger.info("服务器已停止");
    }
} 