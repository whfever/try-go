package org.example.imitate.tomcat.controller;

import org.example.imitate.tomcat.annotation.Controller;
import org.example.imitate.tomcat.annotation.RequestMapping;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

@Controller
public class TestController extends BaseController {
    
    @RequestMapping(value = "/")
    public FullHttpResponse index(FullHttpRequest request) {
        return createResponse(
            "<html><body><h1>Hello from ImitateTomcatComplex!</h1></body></html>",
            "text/html"
        );
    }
    
    @RequestMapping(value = "/test")
    public FullHttpResponse test(FullHttpRequest request) {
        return createResponse("test", "text/plain");
    }
    
    @RequestMapping(value = "/1")
    public FullHttpResponse one(FullHttpRequest request) {
        return createResponse("one", "text/plain");
    }
    
    @RequestMapping(value = "/2")
    public FullHttpResponse two(FullHttpRequest request) {
        return createResponse("two", "text/plain");
    }
} 