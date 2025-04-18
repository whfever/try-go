package org.example.imitate.tomcat.controller;

import org.example.imitate.tomcat.annotation.Controller;
import org.example.imitate.tomcat.annotation.RequestMapping;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
public class UserController extends BaseController {
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @RequestMapping(value = "/api/users", method = "POST")
    public FullHttpResponse createUser(FullHttpRequest request) {
        try {
            // 解析请求体
            String content = request.content().toString(io.netty.util.CharsetUtil.UTF_8);
            ObjectNode requestBody = (ObjectNode) objectMapper.readTree(content);
            
            // 创建响应
            ObjectNode responseBody = objectMapper.createObjectNode();
            responseBody.put("id", 1);
            responseBody.put("username", requestBody.get("username").asText());
            responseBody.put("status", "created");
            
            return createResponse(responseBody.toString(), "application/json");
        } catch (Exception e) {
            ObjectNode errorBody = objectMapper.createObjectNode();
            errorBody.put("error", "Invalid request");
            return createResponse(errorBody.toString(), "application/json");
        }
    }
    
    @RequestMapping(value = "/api/users", method = "GET")
    public FullHttpResponse getUsers(FullHttpRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        response.putArray("users")
            .add(mapper.createObjectNode().put("id", 1).put("username", "user1"))
            .add(mapper.createObjectNode().put("id", 2).put("username", "user2"));
            
        return createResponse(response.toString(), "application/json");
    }
    @RequestMapping(value = "/api/users1", method = "GET")
    public FullHttpResponse getUsers1(FullHttpRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        response.putArray("users")
                .add(mapper.createObjectNode().put("id", 1).put("username", "user1"))
                .add(mapper.createObjectNode().put("id", 2).put("username", "user2"));

        return createResponse(response.toString(), "application/json");
    }
} 