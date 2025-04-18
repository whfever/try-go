package org.example.imitate.tomcat;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class BeforeHttpRequest extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        try {
            logger.info("收到请求: {} {}", request.method(), request.uri());
            
            // 根据路径处理不同的请求
            String uri = request.uri();
            FullHttpResponse response;
            
            if ("/test".equals(uri)) {
                response = createResponse("test", "text/plain");
            } else {
                response = createResponse(
                    "<html><body><h1>Hello from ImitateTomcatComplex!</h1></body></html>",
                    "text/html"
                );
            }
            
            ctx.writeAndFlush(response);
        } catch (Exception e) {
            logger.error("处理请求时发生错误", e);
            sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private FullHttpResponse createResponse(String content, String contentType) {
        FullHttpResponse response = new DefaultFullHttpResponse(
            HttpVersion.HTTP_1_1,
            HttpResponseStatus.OK,
            Unpooled.wrappedBuffer(content.getBytes(StandardCharsets.UTF_8))
        );
        
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType + "; charset=utf-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        
        return response;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("通道异常", cause);
        sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
    }

    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(
            HttpVersion.HTTP_1_1,
            status,
            Unpooled.wrappedBuffer(("Error: " + status.toString()).getBytes(StandardCharsets.UTF_8))
        );
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=utf-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        ctx.writeAndFlush(response);
    }
}